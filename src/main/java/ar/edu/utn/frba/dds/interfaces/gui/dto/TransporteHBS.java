package ar.edu.utn.frba.dds.interfaces.gui.dto;

import java.util.List;

public class TransporteHBS {
    private Integer id = null;
    private String info = null;
    private List<ParadaHBS> paradas = null;
    private Boolean esPublico = null;


    public TransporteHBS() {
    }

    public Integer getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public List<ParadaHBS> getParadas() {
        return paradas;
    }

    public Boolean getEsPublico() {
        return esPublico;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setParadas(List<ParadaHBS> paradas) {
        this.paradas = paradas;
    }

    public void setEsPublico(Boolean esPublico) {
        this.esPublico = esPublico;
    }
}
