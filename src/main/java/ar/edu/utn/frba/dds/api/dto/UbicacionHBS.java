package ar.edu.utn.frba.dds.api.dto;

import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;

import java.util.HashMap;
import java.util.Map;

public class UbicacionHBS {
    private String provincia;
    private String municipio;
    private String localidad;
    private String calle;
    private Integer numero;
    private Float latitud;
    private Float longitud;

    public UbicacionHBS() {
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

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public String getDireccion() {
        return localidad + " - " + calle + " - " + numero;
    }

    public void setDireccion(String localidad, String calle, Integer numero) {
        setLocalidad(localidad);
        setCalle(calle);
        setNumero(numero);
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
}