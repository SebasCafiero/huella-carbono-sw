package ar.edu.utn.frba.dds.lugares;

import java.util.HashSet;
import java.util.Set;

public class UbicacionGeografica {
    public Set<Organizacion> organizaciones;
    private String localidad;
    private Coordenada coordenada;

    public UbicacionGeografica(String lugar, Coordenada coordenada) {
        this.localidad = lugar;
        this.coordenada = coordenada;
        this.organizaciones = new HashSet<>();
    }

    public UbicacionGeografica(String lugar, Float latitud, Float longitud){
        this.localidad = lugar;
        this.coordenada = new Coordenada(latitud,longitud);
        this.organizaciones = new HashSet<>();
    }

    public String getLocalidad() {
        return localidad;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void agregarOrganizacion(Organizacion organizacion) {
        this.organizaciones.add(organizacion);
    }

    public Set<Organizacion> getOrganizaciones() {
        return organizaciones;
    }
}
