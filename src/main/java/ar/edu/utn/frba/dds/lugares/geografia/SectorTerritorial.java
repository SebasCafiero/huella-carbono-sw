package ar.edu.utn.frba.dds.lugares.geografia;

import ar.edu.utn.frba.dds.lugares.Organizacion;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class SectorTerritorial {

    protected String nombre;
    protected Set<Organizacion> organizaciones;

    public Set<Organizacion> getOrganizaciones() {
        return this.organizaciones;
    }
    public void agregarOrganizacion(Organizacion unaOrganizacion) {
        this.organizaciones.add(unaOrganizacion);
    }

    public Set<UbicacionGeografica> ubicaciones() {
        return this.organizaciones.stream().map(o -> o.getUbicacion()).collect(Collectors.toSet());
    }

    public String getNombre() {
        return nombre;
    }

}
