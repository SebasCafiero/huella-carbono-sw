package ar.edu.utn.frba.dds.interfaces.input.json;

import ar.edu.utn.frba.dds.interfaces.SectorRequest;

import java.util.List;

public class OrganizacionJSONDTO {
    private String nombre;
    private UbicacionJSONDTO ubicacion;
    private String clasificacion;
    private String tipo;
    private List<SectorRequest> sectores;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<SectorRequest> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorRequest> sectores) {
        this.sectores = sectores;
    }
}
