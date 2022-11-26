package ar.edu.utn.frba.dds.interfaces.gui.dto;

import java.util.List;

public class TrayectoHBS {
    private String fecha;
    private Integer id;
    private List<TramoHBS> tramos;
    private List<MiembroHBS> miembros;

    public TrayectoHBS() {
    }

    public Integer getId() {
        return id;
    }

    public List<TramoHBS> getTramos() {
        return tramos;
    }

    public List<MiembroHBS> getMiembros() {
        return miembros;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTramos(List<TramoHBS> tramos) {
        this.tramos = tramos;
    }

    public void setMiembros(List<MiembroHBS> miembros) {
        this.miembros = miembros;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setFecha(Integer mes, Integer anio) {
        this.fecha = mes + "/" + anio;
    }
}