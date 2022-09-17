package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
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
    private final RepoCategorias repoCategorias;

    public FachadaOrganizacion() {
        repoFactores = (RepoFactores) FactoryRepositorio.get(FactorEmision.class);
        repoCategorias = (RepoCategorias) FactoryRepositorio.get(Categoria.class);
    }

    @Override
    public void cargarParametros(Map<String, Float> parametrosSistema) {
        parametrosSistema.forEach(this::cargarParametro);
    }

    @Override
    public Float obtenerHU(Collection<Medible> mediciones) {
        return (float) mediciones.stream().mapToDouble(medible -> {
            Optional<Categoria> categoria = repoCategorias
                    .findByNombreCategoria(medible.getCategoria());

            if(!categoria.isPresent()) {
                throw new MedicionSinFactorEmisionException(medible.getCategoria());
            }

            FactorEmision factorEmision = this.repoFactores
                    .findByCategoria(categoria.get()).get(0);

            return factorEmision.getValor() * medible.getValor();
        }).reduce(0, Double::sum);
    }


    public void cargarParametro(String nombreFactor, Float valor) {
        String[] categoriaString = nombreFactor.split(":");
        String[] subCategoria = categoriaString[0].split("->");
        String unidad = categoriaString[1].trim();

        Optional<Categoria> optCategoria = repoCategorias
                .findByActividadAndTipoConsumo(subCategoria[0].trim(), subCategoria[1].trim());

        if(optCategoria.isPresent()) {
            FactorEmision factorEmision = this.repoFactores.findByCategoria(optCategoria.get()).get(0);
            factorEmision.setValor(valor);
        } else {
            Categoria categoria = new Categoria(subCategoria[0].trim(), subCategoria[1].trim());
            this.repoCategorias.agregar(categoria);
            this.repoFactores.agregar(new FactorEmision(categoria, unidad, valor));
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
