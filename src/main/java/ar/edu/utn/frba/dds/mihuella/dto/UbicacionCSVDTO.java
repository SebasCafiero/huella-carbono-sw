package ar.edu.utn.frba.dds.mihuella.dto;

import java.io.Serializable;

public class UbicacionCSVDTO implements Serializable {
    private String pais;
    private String provincia;
    private String municipio;
    private String localidad;
    private Float latitud;
    private Float longitud;

    public String getPais() {
        return pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Float getLatitudBase() {
        return latitud;
    }

    public Float getLongitudBase() {
        return longitud;
    }
}
