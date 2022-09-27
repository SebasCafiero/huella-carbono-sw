package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.RepoCategorias;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import java.util.*;
import java.util.stream.Collectors;

public class FachadaOrganizacion implements FachadaOrg {
    private final RepoFactores repoFactores;
//    private final RepoCategorias repoCategorias;

    public FachadaOrganizacion() {
        repoFactores = (RepoFactores) FactoryRepositorio.get(FactorEmision.class);
//        repoCategorias = (RepoCategorias) FactoryRepositorio.get(Categoria.class);
    }

    public FachadaOrganizacion(RepoFactores repoFactores) {
        this.repoFactores = repoFactores;
    }

    @Override
    public void cargarParametros(Map<String, Float> parametrosSistema) {
        parametrosSistema.forEach(this::cargarParametro);
    }

    @Override
    public Float obtenerHU(Collection<Medible> mediciones) {
        return (float) mediciones.stream().mapToDouble(medible -> {
            String[] subCategoria = medible.getCategoria().split("-", 2);
            Categoria categoria = new Categoria(subCategoria[0].trim(), subCategoria[1].trim());

            return medible.getValor() * repoFactores.findByCategoria(categoria).stream().findFirst()
                    .orElseThrow(() -> new MedicionSinFactorEmisionException(medible.getCategoria()))
                    .getValor();
        }).reduce(0, Double::sum);
    }

    public void cargarParametro(String nombreFactor, Float valor) {
        String[] categoriaString = nombreFactor.split(":");
        String[] subCategoria = categoriaString[0].split("->");
        String unidad = categoriaString[1].trim();

        Categoria categoria = new Categoria(subCategoria[0].trim(), subCategoria[1].trim());
        Optional<FactorEmision> optFactor = repoFactores
                .findByCategoria(categoria).stream().findFirst();

        if(optFactor.isPresent()) {
            optFactor.get().setValor(valor);
        } else {
            this.repoFactores.agregar(new FactorEmision(categoria, unidad, valor));
        }
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

    public void mostrarParametros() {
        repoFactores.buscarTodos().forEach(System.out::println);
    }

    public Float getNuevoImpactoOrganizacion(Organizacion org, Periodo periodo) {
        return getNuevoImpactoMedicionesOrganizacion(org, periodo) +
                getNuevoImpactoTrayectosOrganizacion(org, periodo);
    }

    private Float getNuevoImpactoMedicionesOrganizacion(Organizacion org, Periodo periodo) {
//        return obtenerHU(org.getMediciones().stream()
//                .filter(me -> me.perteneceAPeriodo(periodo.getAnio(), periodo.getMes()))
//                .map(me -> {
//                    return new Medicion(me.getMiCategoria(), me.getUnidad(),
//                            me.getValor() * factorEquivalencia(periodo, me.getPeriodo()));
//                })
//                .collect(Collectors.toList()));
        return org.getMediciones().stream()
                .map(me -> obtenerHU(Collections.singletonList(me)) *
                        factorEquivalencia(periodo, me.getPeriodo()))
                .reduce(Float::sum).orElse(0F);
    }

    private Float getNuevoImpactoTrayectosOrganizacion(Organizacion org, Periodo periodo) {
        return org.getSectores().stream()
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .map(miembro -> miembro.getTrayectos().stream()
                        .map(trayecto -> obtenerHU(new ArrayList<>(trayecto.getTramos())) /
                                trayecto.cantidadDeMiembros())
                        .reduce(Float::sum).orElse(0F) / miembro.cantidadDeOrganizacionesDondeTrabaja()
                ).reduce(Float::sum).orElse(0F);
    }

    private float factorEquivalencia(Periodo periodoDeseado, Periodo periodoMedible) {
        if(!periodoDeseado.getAnio().equals(periodoMedible.getAnio()))
            return 0;

        if(periodoDeseado.getPeriodicidad().equals('A')) {
            return 1;
        }

        if(periodoMedible.getPeriodicidad().equals('A')) {
            return 1/12F;
        }

        if(!periodoDeseado.getMes().equals(periodoMedible.getMes())) {
            return 0;
        }

        return 1;
    }
}
