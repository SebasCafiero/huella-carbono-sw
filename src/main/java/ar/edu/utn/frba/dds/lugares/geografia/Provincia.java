package ar.edu.utn.frba.dds.lugares.geografia;

import java.util.HashSet;

public class Provincia extends SectorTerritorial {

    private String nombrePais;

    public Provincia(String nombre, String pais) {
        this.nombre = nombre;
        this.nombrePais = pais;
        this.organizaciones = new HashSet<>();
    }

    public String getNombrePais() {
        return nombrePais;
    }
}
