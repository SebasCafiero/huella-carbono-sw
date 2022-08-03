package ar.edu.utn.frba.dds.lugares.geografia;

import java.util.HashSet;

public class Municipio extends SectorTerritorial {

    private Provincia provincia;

    public Municipio(String nombre, String provincia, String pais) {
        this.nombre = nombre;
        this.provincia = new Provincia(provincia, pais);
        this.organizaciones = new HashSet<>();
    }

}
