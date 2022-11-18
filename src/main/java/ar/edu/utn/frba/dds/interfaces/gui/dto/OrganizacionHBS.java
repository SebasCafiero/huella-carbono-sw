package ar.edu.utn.frba.dds.interfaces.gui.dto;

public class OrganizacionHBS {
    private String razonSocial;
    private Integer id;
    private UbicacionHBS ubicacion;

    public OrganizacionHBS() {
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UbicacionHBS getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionHBS ubicacion) {
        this.ubicacion = ubicacion;
    }
}
