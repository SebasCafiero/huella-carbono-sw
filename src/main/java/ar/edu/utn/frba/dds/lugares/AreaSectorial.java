package ar.edu.utn.frba.dds.lugares;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AreaSectorial {
    private String nombre;
    private Set<Organizacion> organizaciones;

    public AreaSectorial(String nombre) {
        this.nombre = nombre;
        this.organizaciones = new HashSet<>();
    }

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
