package ar.edu.utn.frba.dds.lugares.geografia;

import java.util.HashSet;
import java.util.Set;

public class Provincia extends AreaSectorial {

    private String nombrePais;
    private Set<Municipio> municipios;

    public Provincia(String nombre, String pais) {
        this.nombre = nombre;
        this.nombrePais = pais;
        this.organizaciones = new HashSet<>();
        this.municipios = new HashSet<>();
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void agregarMunicipio(Municipio unMunicipio) {
        this.municipios.add(unMunicipio);
    }
}
