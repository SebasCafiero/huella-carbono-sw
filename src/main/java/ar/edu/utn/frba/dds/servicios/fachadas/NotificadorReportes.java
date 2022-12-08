package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.entities.medibles.ReporteArea;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;

public interface NotificadorReportes {

    void notificarReporte(AgenteSectorial agente, ReporteArea reporte);
}
