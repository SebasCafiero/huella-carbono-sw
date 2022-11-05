package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportes;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

public class FachadaReportes {
    private NotificadorReportes notificadorReportes;
    private final FachadaOrganizacion fachadaOrganizacion;
    private ReporteOrganizacion reporteOrganizacion = null;

    public FachadaReportes() {
        this.fachadaOrganizacion = new FachadaOrganizacion();
    }

    public FachadaReportes setNotificador(NotificadorReportes notificador) {
        this.notificadorReportes = notificador;
        return this;
    }

    public void notificarReporteAgente(AreaSectorial area, Integer anio, Integer mes) {
        this.notificadorReportes.notificarReporte(area.getAgente(), generarReporteAgente(area, anio, mes));
    }

    public ReporteAgente generarReporteAgente(AreaSectorial area, Integer anio, Integer mes) {
        HashMap<Organizacion, Float> mapaConsumos = new HashMap<>();
        area.getOrganizaciones().forEach((organizacion -> {
            mapaConsumos.put(organizacion, fachadaOrganizacion
                    .calcularImpactoOrganizacion(organizacion, new Periodo(anio, mes)));
        }));

        Float total = mapaConsumos.values().stream().reduce(Float::sum).orElse(0F);

        ReporteAgente reporte = new ReporteAgente(mapaConsumos, area, total);

        if(area.getAgente() != null) {
            area.getAgente().agregarReporte(reporte);
        }

        return reporte;
    }

    public ReporteOrganizacion generarReporteOrganizacion(Organizacion organizacion, Periodo periodo) {
        ReporteOrganizacion reporte = new ReporteOrganizacion();

        Map<Categoria, Float> consumoPorCategoria = new HashMap<>();
        Map<Sector, Float> consumoPorSector = new HashMap<>();
        Map<Miembro, Float> consumoPorMiembro = new HashMap<>();

        Float totalTrayectos = organizacion.getSectores().stream().map(sector -> {
            Float consumoSector = sector.getListaDeMiembros().stream().map(miembro -> {
                Float consumoMiembro = miembro.getTrayectos().stream()
                        .flatMap(trayecto -> trayecto.getTramos().stream()).map(tramo -> {
                            final float consumoTramo = fachadaOrganizacion.obtenerHU(Collections.singletonList(tramo)) /
                                    fachadaOrganizacion.factorProporcionalTrayecto(tramo.getTrayecto(), miembro, periodo);

                            consumoPorCategoria.putIfAbsent(tramo.getMiCategoria(), 0F);
                            consumoPorCategoria.compute(tramo.getMiCategoria(), (anterior, nuevo) -> +consumoTramo);

                            return consumoTramo;
                        }).reduce(Float::sum).orElse(0F);
                consumoPorMiembro.put(miembro, consumoMiembro);
                return consumoMiembro;
            }).reduce(Float::sum).orElse(0F);
            consumoPorSector.put(sector, consumoSector);
            return consumoSector;
        }).reduce(Float::sum).orElse(0F);

        Float totalMediciones = organizacion.getMediciones().stream().map(me -> {
            final float consumoMedicion = fachadaOrganizacion.obtenerHU(Collections.singletonList(me)) *
                    fachadaOrganizacion.factorEquivalenciaPeriodos(periodo, me.getPeriodo());

            consumoPorCategoria.putIfAbsent(me.getMiCategoria(), 0F);
            consumoPorCategoria.compute(me.getMiCategoria(), (anterior, nuevo) -> +consumoMedicion);

            return consumoMedicion;
        }).reduce(Float::sum).orElse(0F);

        reporte.setConsumoPorSector(consumoPorSector);
        reporte.setConsumoPorMiembro(consumoPorMiembro);
        reporte.setConsumoPorCategoria(consumoPorCategoria);
        reporte.setConsumoTotal(totalTrayectos + totalMediciones);
        reporte.setConsumoMediciones(totalMediciones);
        reporte.setFechaCreacion(LocalDate.now());
//        organizacion.agregarReporte(reporte);

        return reporte;
//        this.notificadorReportes.notificarReporteOrganizacion(area.getAgente(), reporte);
    }

    public void generarReporteOrganizacion2(Organizacion organizacion, Periodo periodo) {
        ReporteOrganizacion reporte = new ReporteOrganizacion();

        Map<Categoria, Float> consumoPorCategoria = new HashMap<>();
        Map<Sector, Float> consumoPorSector = new HashMap<>();
        Map<Miembro, Float> consumoPorMiembro = new HashMap<>();

        Float totalTrayectos = organizacion.getSectores().stream().map(sector -> {
            Float consumoSector = sector.getListaDeMiembros().stream().map(miembro -> {
                Float consumoMiembro = miembro.getTrayectos().stream()
                        .flatMap(trayecto -> trayecto.getTramos().stream()).map(tramo -> {
                            final float consumoTramo = fachadaOrganizacion.obtenerHU(Collections.singletonList(tramo)) /
                                    fachadaOrganizacion.factorProporcionalTrayecto(tramo.getTrayecto(), miembro, periodo);

                            consumoPorCategoria.putIfAbsent(tramo.getMiCategoria(), 0F);
                            consumoPorCategoria.compute(tramo.getMiCategoria(), (anterior, nuevo) -> +consumoTramo);

                            return consumoTramo;
                        }).reduce(Float::sum).orElse(0F);
                consumoPorMiembro.put(miembro, consumoMiembro);
                return consumoMiembro;
            }).reduce(Float::sum).orElse(0F);
            consumoPorSector.put(sector, consumoSector);
            return consumoSector;
        }).reduce(Float::sum).orElse(0F);

        Float totalMediciones = organizacion.getMediciones().stream().map(me -> {
            final float consumoMedicion = fachadaOrganizacion.obtenerHU(Collections.singletonList(me)) *
                    fachadaOrganizacion.factorEquivalenciaPeriodos(periodo, me.getPeriodo());

            consumoPorCategoria.putIfAbsent(me.getMiCategoria(), 0F);
            consumoPorCategoria.compute(me.getMiCategoria(), (anterior, nuevo) -> +consumoMedicion);

            return consumoMedicion;
        }).reduce(Float::sum).orElse(0F);

        reporte.setConsumoPorSector(consumoPorSector);
        reporte.setConsumoPorMiembro(consumoPorMiembro);
        reporte.setConsumoPorCategoria(consumoPorCategoria);
        reporte.setConsumoTotal(totalTrayectos + totalMediciones);
        reporte.setConsumoMediciones(totalMediciones);
        reporte.setFechaCreacion(LocalDate.now());
        reporte.setPeriodoReferencia(periodo);
//        organizacion.agregarReporte(reporte);

        this.reporteOrganizacion = reporte;
//        this.notificadorReportes.notificarReporteOrganizacion(area.getAgente(), reporte);
    }

    public ReporteOrganizacion getReporteOrganizacion() {
        return reporteOrganizacion;
    }
}

// Implementing collectors
//Map<Categoria, Float> consumoPorCategoria =
//        organizacion.getSectores().stream()
//        .flatMap(sector -> sector.getListaDeMiembros().stream())
//        .map(miembro -> miembro.getTrayectos().stream()
//                .flatMap(trayecto -> trayecto.getTramos().stream())
//                .map(tramo -> {
//                    Float consumoTramo = fachadaOrganizacion.obtenerHU(Collections.singletonList(tramo)) *
//                            fachadaOrganizacion.factorEquivalencia(periodo, tramo.getTrayecto().getPeriodo()) /
//                            tramo.getTrayecto().cantidadDeMiembros();
//                    return new AbstractMap.SimpleEntry<>(tramo.getMiCategoria(), consumoTramo);
//                }).collect(Collector.of(
//                        HashMap<Categoria,Float>::new,
//                        (mapa, mapita) -> {
//                            Categoria categoria = mapita.getKey();
//                            mapa.putIfAbsent(categoria, 0F);
//                            mapa.compute(categoria, (anterior, nuevo) -> + mapita.getValue());
//                        },
//                        (mapa1, mapa2) -> {
//                            mapa1.putAll(mapa2);
//                            return mapa1;
//                        })
//                )
//        ).collect(Collector.of(
//                HashMap::new,
//                (mapa1, mapa2) -> mapa2.forEach((key, value) -> mapa1.merge(key, value, Float::sum)),
//                (mapa1, mapa2) -> { mapa1.putAll(mapa2); return mapa1; }
//                ));
