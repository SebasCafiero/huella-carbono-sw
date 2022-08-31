package ar.edu.utn.frba.dds.mihuella.dto;

import java.io.Serializable;

public class MedicionCSVDTO implements Serializable {
    private String actividad;
    private String tipoConsumo;
    private String unidad;
    private String datoActividad;
    private String periodicidad;
    private String periodo;

    public MedicionCSVDTO() { }

    public MedicionCSVDTO(String actividad, String tipoActividad, String unidad, String datoActividad, String periodicidad, String periodo) {
        this.actividad = actividad;
        this.tipoConsumo = tipoActividad;
        this.unidad = unidad;
        this.datoActividad = datoActividad;
        this.periodicidad = periodicidad;
        this.periodo = periodo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDatoActividad() {
        return datoActividad;
    }

    public void setDatoActividad(String datoActividad) {
        this.datoActividad = datoActividad;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "MedicionCSVDTO{" +
                "actividad='" + actividad + '\'' +
                ", tipoConsumo='" + tipoConsumo + '\'' +
                ", unidad='" + unidad + '\'' +
                ", datoActividad='" + datoActividad + '\'' +
                ", periodicidad='" + periodicidad + '\'' +
                ", periodo='" + periodo + '\'' +
                '}';
    }
}
