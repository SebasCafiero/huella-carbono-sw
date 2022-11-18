package ar.edu.utn.frba.dds.interfaces.gui.dto;

import java.util.List;

public class TrayectoHBS {
    private Integer id;
    private Integer mes;
    private Integer año;
    private List<TramoHBS> tramos;
    private List<MiembroHBS> miembros;

    public TrayectoHBS() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getAño() {
        return año;
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

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public void setTramos(List<TramoHBS> tramos) {
        this.tramos = tramos;
    }

    public void setMiembros(List<MiembroHBS> miembros) {
        this.miembros = miembros;
    }
}