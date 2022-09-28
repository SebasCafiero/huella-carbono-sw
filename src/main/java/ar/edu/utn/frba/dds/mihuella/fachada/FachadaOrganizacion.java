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
        Optional<FactorEmision> optFactor = repoFactores.findByCategoria(categoria).stream().findFirst();

        if(optFactor.isPresent()) {
            optFactor.get().setValor(valor);
        } else {
            this.repoFactores.agregar(new FactorEmision(categoria, unidad, valor));
        }
    }

    public void mostrarParametros() {
        repoFactores.buscarTodos().forEach(System.out::println);
    }

    public Float calcularImpactoOrganizacion(Organizacion organizacion, Periodo periodo) {
        return calcularImpactoMediciones(organizacion, periodo) +
                calcularImpactoTrayectos(organizacion, periodo);
    }

    private Float calcularImpactoMediciones(Organizacion organizacion, Periodo periodo) {
        return organizacion.getMediciones().stream()
                .map(me -> obtenerHU(Collections.singletonList(me)) *
                        factorEquivalencia(periodo, me.getPeriodo()))
                .reduce(Float::sum).orElse(0F);
    }

    public Float calcularImpactoTrayectos(Miembro miembro, Periodo periodo) {
        return miembro.getTrayectos().stream()
                .map(trayecto -> obtenerHU(new ArrayList<>(trayecto.getTramos())) /
                        miembro.cantidadDeOrganizacionesDondeTrabaja() /
                        trayecto.cantidadDeMiembros() *
                        factorEquivalencia(periodo, trayecto.getPeriodo())
                ).reduce(Float::sum).orElse(0F);
    }

    public Float calcularImpactoTrayectos(Sector sector, Periodo periodo) {
        return sector.getListaDeMiembros().stream()
                .map(miembro -> calcularImpactoTrayectos(miembro, periodo))
                .reduce(Float::sum).orElse(0F);
    }

    private Float calcularImpactoTrayectos(Organizacion org, Periodo periodo) {
        return org.getSectores().stream()
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .map(miembro -> miembro.getTrayectos().stream()
                        .map(trayecto -> obtenerHU(new ArrayList<>(trayecto.getTramos())) /
                                miembro.cantidadDeOrganizacionesDondeTrabaja() /
                                trayecto.cantidadDeMiembros() *
                                factorEquivalencia(periodo, trayecto.getPeriodo())
                        ).reduce(Float::sum).orElse(0F)
                ).reduce(Float::sum).orElse(0F);
    }

    public float factorEquivalencia(Periodo periodoDeseado, Periodo periodoMedible) {
        if(!periodoDeseado.getAnio().equals(periodoMedible.getAnio()))
            return 0;

        if(periodoDeseado.getPeriodicidad().equals('A'))
            return 1;

        if(periodoMedible.getPeriodicidad().equals('A'))
            return 1/12F;

        if(!periodoDeseado.getMes().equals(periodoMedible.getMes()))
            return 0;

        return 1;
    }
}
