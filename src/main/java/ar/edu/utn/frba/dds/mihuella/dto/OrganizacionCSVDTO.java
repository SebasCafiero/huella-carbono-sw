package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;

public class OrganizacionCSVDTO {
    private String organizacion;
    private String ubicacion;
    private String clasificacion;
    private String latitud;
    private String longitud;
    private String tipo;
    private List<SectorCSVDTO> sectores;

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<SectorCSVDTO> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorCSVDTO> sectores) {
        this.sectores = sectores;
    }
}