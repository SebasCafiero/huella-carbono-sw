package ar.edu.utn.frba.dds.interfaces.gui.dto;

public class ParadaHBS {
    private Integer id;
    private UbicacionHBS ubicacion;

    public ParadaHBS() {
    }

    public Integer getId() {
        return id;
    }

    public UbicacionHBS getUbicacion() {
        return ubicacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUbicacion(UbicacionHBS ubicacion) {
        this.ubicacion = ubicacion;
    }
}