package ar.edu.utn.frba.dds.entities.transportes;

import java.util.Objects;

public class TipoServicio {
    private final String nombre;

    public TipoServicio(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TipoServicio other = (TipoServicio) obj;
        return Objects.equals(nombre, other.nombre);
    }
}

/* Ejemplos de uso:
* taxi = new TipoServicio("taxi");
* remis = new TipoServicio("remis");
* uber = new TipoServicio("uber");
* didi = new TipoServicio("didi");
* cabify = new TipoServicio("cabify");
* */
