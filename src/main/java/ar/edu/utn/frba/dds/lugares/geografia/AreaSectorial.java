package ar.edu.utn.frba.dds.lugares.geografia;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.mediciones.Reporte;
import ar.edu.utn.frba.dds.personas.AgenteSectorial;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AreaSectorial {
    protected String nombre;
    protected Set<Organizacion> organizaciones;

    protected Set<AgenteSectorial> agentes;
    protected List<Reporte> reportes;

    public Set<Organizacion> getOrganizaciones() {
        return this.organizaciones;
    }
    public void agregarOrganizacion(Organizacion unaOrganizacion) {
        this.organizaciones.add(unaOrganizacion);
    }

    public Set<UbicacionGeografica> ubicaciones() {
        return this.organizaciones.stream().map(o -> o.getUbicacion()).collect(Collectors.toSet());
    }

    public void agregarAgente(AgenteSectorial unAgente) {
        this.agentes.add(unAgente);
    }

    public void agregarReporte(Reporte unReporte) {
        this.reportes.add(unReporte);
    }

    public String getNombre() {
        return nombre;
    }

    public Set<AgenteSectorial> getAgentes() {
        return agentes;
    }

    public List<Reporte> getReportes() {
        return reportes;
    }
}
