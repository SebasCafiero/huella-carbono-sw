package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;

public class OrganizacionJSONDTO {
    private String organizacion;
    private UbicacionJSONDTO ubicacion;
    private String clasificacion;
    private String tipo;
    private List<SectorDTO> sectores;

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public UbicacionJSONDTO getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionJSONDTO ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<SectorDTO> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorDTO> sectores) {
        this.sectores = sectores;
    }
}
