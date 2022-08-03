package ar.edu.utn.frba.dds.servicios.reportes;

import ar.edu.utn.frba.dds.mediciones.Reporte;
import ar.edu.utn.frba.dds.personas.AgenteSectorial;

public interface NotificadorReportes {

    void notificarReporte();

    NotificadorReportes setInformacionReporte(Reporte crearReporte);

    NotificadorReportes setAgente(AgenteSectorial agente);

}