package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import java.util.*;
import java.util.stream.Collectors;

public class FachadaOrganizacion implements FachadaOrg {
    private final RepoFactores repoFactores;

    public FachadaOrganizacion() {
        repoFactores = (RepoFactores) FactoryRepositorio.get(FactorEmision.class);
    }

    @Override
    public void cargarParametros(Map<String, Float> parametrosSistema) {
        parametrosSistema.forEach(this::cargarParametro);
    }

    @Override
    public Float obtenerHU(Collection<Medible> mediciones) {
        return (float) mediciones.stream().mapToDouble(medible -> {
//            Optional<FactorEmision> factorEmision = this.repoFactores.buscarTodos().stream()
//                    .filter(factor -> factor.getCategoria().equals(medible.getCategoria()))
//                    .findFirst();
            Optional<FactorEmision> factorEmision = this.repoFactores.findByCategoria(medible.getCategoria())
                    .stream().findFirst();

            if(!factorEmision.isPresent()) {
                throw new MedicionSinFactorEmisionException(medible.getCategoria());
            }

            return factorEmision.get().getValor() * medible.getValor();
        }).reduce(0, Double::sum);
    }

    //TODO la fachada deberia ser para una organizacion especifica, deberia ser atributo
    public Float obtenerConsumoTotalTrayectosOrganizacion(Organizacion unaOrg) throws MedicionSinFactorEmisionException {
        Float consumo = 0F;
        for(Trayecto unTrayecto : unaOrg.trayectosDeMiembros()) {
            consumo += obtenerHU(new ArrayList<>(unTrayecto.getTramos())) / unTrayecto.cantidadDeMiembros();
            System.out.println("CONSUMO CADA TRAYECTO DE ORG: " + consumo);
        }
        System.out.println("CONSUMO TRAYECTOS TOTAL DE ORG: " + consumo);
        return consumo;
    }

    public Float obtenerImpactoMiembroEnTrayectos(Organizacion unaOrg, Miembro unMiembro) throws MedicionSinFactorEmisionException {
        Float consumoMiembro = 0F;
        for(Trayecto unTrayecto : unMiembro.getTrayectos()){
            consumoMiembro += obtenerHU(new ArrayList<>(unTrayecto.getTramos())) / unTrayecto.cantidadDeMiembros();
            System.out.println("Consumo Miembro " + unMiembro.getNroDocumento() + " Cada Trayecto: " + consumoMiembro);
        }
        System.out.println("Consumo Miembro " + unMiembro.getNroDocumento() + " Total: " + consumoMiembro);
        return consumoMiembro / obtenerConsumoTotalTrayectosOrganizacion(unaOrg);
    }

    public Float obtenerImpactoSector(Organizacion unaOrg, Sector unSector) throws MedicionSinFactorEmisionException {
        Float impactoMiembro = 0F;
        for(Miembro unMiembro : unSector.getListaDeMiembros()){
            impactoMiembro += obtenerImpactoMiembroEnTrayectos(unaOrg,unMiembro);
        }
        return impactoMiembro;
    }

    public void cargarParametro(String nombreFactor, Float valor) {
        String[] categoriaString = nombreFactor.split(":");
        String[] subCategoria = categoriaString[0].split("->");

        Optional<FactorEmision> factorAnterior = this.repoFactores.buscarTodos().stream()
                .filter(factor -> factor.getCategoria().equals(categoriaString[0]))
                .findFirst();
        if(factorAnterior.isPresent()) {
            factorAnterior.get().setValor(valor);
        } else {
            this.repoFactores.agregar(
                    new FactorEmision(new Categoria(subCategoria[0].trim(), subCategoria[1].trim()),
                            categoriaString[1].trim(), valor));
        }
    }

    public Float getImpactoOrganizacion(Organizacion org, Integer anio, Integer mes) {
        return getImpactoMedicionesOrganizacion(org, anio, mes) + getImpactoTrayectosOrganizacion(org, anio, mes);
    }

    public Float getImpactoMedicionesOrganizacion(Organizacion org, Integer anio, Integer mes) {
        return obtenerHU(org.getMediciones().stream()
                .filter(me -> me.perteneceAPeriodo(anio, mes)).collect(Collectors.toList()));
    }

    public Float getImpactoTrayectosOrganizacion(Organizacion org, Integer anio, Integer mes) {
        return (float) org.getSectores().stream()
                .mapToDouble(sector -> getImpactoSector(sector, anio, mes))
                .reduce(Double::sum).orElse(0);
    }

    public Float getTrayectosDeMiembro(Miembro miembro, Integer anio, Integer mes) {
        return (float) miembro.getTrayectos().stream()
                .filter(tr -> tr.perteneceAPeriodo(anio, mes))
                .mapToDouble(tr -> obtenerHU(new ArrayList<>(tr.getTramos())) / tr.cantidadDeMiembros())
                .reduce(Double::sum).orElse(0);
    }

    public Float getImpactoSector(Sector unSector, Integer anio, Integer mes) {
        return (float) unSector.getListaDeMiembros().stream()
                .mapToDouble(mi -> getImpactoMiembroEnPeriodo(mi, anio, mes)
                        / mi.cantidadDeOrganizacionesDondeTrabaja())
                .reduce(Double::sum).orElse(0);
    }

    public Float getImpactoMiembroEnPeriodo(Miembro miembro, Integer anio, Integer mes) {
        return (float) miembro.getTrayectos().stream()
                .filter(tr -> tr.perteneceAPeriodo(anio, mes))
                .mapToDouble(tr -> obtenerHU(new ArrayList<>(tr.getTramos()))
                        / tr.cantidadDeMiembros())
                .reduce(Double::sum).orElse(0);
    }

    public List<Medible> getMediblesDePeriodo(Organizacion organizacion, Integer anio, Integer mes) {
        List<Medible> mediciones = getMedicionesDePeriodo(organizacion, anio, mes);
        mediciones.addAll(getTramosDePeriodo(organizacion, anio, mes));

        return mediciones;
    }

    private List<Medible> getMedicionesDePeriodo(Organizacion organizacion, Integer anio, Integer mes) {
        return organizacion.getMediciones().stream()
                .filter(me -> me.perteneceAPeriodo(anio, mes))
                .collect(Collectors.toList());
    }

    private List<Medible> getTramosDePeriodo(Organizacion organizacion, Integer anio, Integer mes) {
        return organizacion.getMiembros().stream()
                .flatMap(mi -> mi.getTrayectos().stream())
                .filter(tr -> tr.perteneceAPeriodo(anio, mes))
                .flatMap(tr -> tr.getTramos().stream())
                .collect(Collectors.toList());
    }
}
