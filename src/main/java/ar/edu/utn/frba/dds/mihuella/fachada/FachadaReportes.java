package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportes;

import java.util.HashMap;

public class FachadaReportes {
    private NotificadorReportes notificadorReportes;
    private FachadaOrganizacion fachadaOrganizacion;

    public FachadaReportes() {
        this.fachadaOrganizacion = new FachadaOrganizacion();
    }

    public FachadaReportes setNotificador(NotificadorReportes notificador) {
        this.notificadorReportes = notificador;
        return this;
    }

    public void realizarReporte(AreaSectorial area, Integer anio, Integer mes) {
        HashMap<Organizacion,Float> mapaConsumos = new HashMap<>();
        area.getOrganizaciones().forEach((organizacion -> {
            mapaConsumos.put(organizacion, fachadaOrganizacion
                    .getNuevoImpactoOrganizacion(organizacion, new Periodo(anio, mes)));
        }));

        Float total = mapaConsumos.values().stream().reduce(Float::sum).orElse(0F);

        ReporteAgente reporte = new ReporteAgente(mapaConsumos, area, total);
        this.notificadorReportes.notificarReporte(area.getAgente(), reporte);
    }
}
