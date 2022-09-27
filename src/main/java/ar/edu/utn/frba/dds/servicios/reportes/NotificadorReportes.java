package ar.edu.utn.frba.dds.servicios.reportes;

import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;

public interface NotificadorReportes {

    void notificarReporte(AgenteSectorial agente, ReporteAgente reporte);
}
