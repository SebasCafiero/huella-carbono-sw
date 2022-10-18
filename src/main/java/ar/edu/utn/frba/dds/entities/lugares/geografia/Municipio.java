package ar.edu.utn.frba.dds.entities.lugares.geografia;

import java.util.HashSet;

public class Municipio extends AreaSectorial {
    private Provincia provincia;

    public Municipio(String nombre, String provincia, String pais) {
        this.nombre = nombre;
        this.provincia = new Provincia(provincia, pais);
        this.organizaciones = new HashSet<>();
    }

    public Provincia getProvincia() {
        return provincia;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "nombre='" + nombre + '\'' +
                ", provincia=" + provincia +
                "}";
    }
}
