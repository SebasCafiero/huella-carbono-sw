package ar.edu.utn.frba.dds.interfaces.input.json;

import ar.edu.utn.frba.dds.interfaces.SectorRequest;
import ar.edu.utn.frba.dds.interfaces.input.SectorResponse;

import java.util.List;

public class OrganizacionResponse {
    private String organizacion;
    private UbicacionJSONDTO ubicacion;
    private String clasificacion;
    private String tipo;
    private List<SectorResponse> sectores;

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

    public List<SectorResponse> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorResponse> sectores) {
        this.sectores = sectores;
    }
}
