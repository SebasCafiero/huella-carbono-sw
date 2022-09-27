package ar.edu.utn.frba.dds.entities.lugares.geografia;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AreaSectorial {
    protected String nombre;
    protected Set<Organizacion> organizaciones;

    protected AgenteSectorial agente;
    protected List<ReporteAgente> reportes;

    public Set<Organizacion> getOrganizaciones() {
        return this.organizaciones;
    }
    public void agregarOrganizacion(Organizacion unaOrganizacion) {
        this.organizaciones.add(unaOrganizacion);
    }

    public Set<UbicacionGeografica> ubicaciones() {
        return this.organizaciones.stream().map(o -> o.getUbicacion()).collect(Collectors.toSet());
    }

    public AgenteSectorial getAgente() {
        return agente;
    }

    public void setAgente(AgenteSectorial agente) {
        this.agente = agente;
    }

    public void agregarReporte(ReporteAgente unReporte) {
        this.reportes.add(unReporte);
    }

    public String getNombre() {
        return nombre;
    }


    public List<ReporteAgente> getReportes() {
        return reportes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOrganizaciones(Set<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }

    public void setReportes(List<ReporteAgente> reportes) {
        this.reportes = reportes;
    }
}
