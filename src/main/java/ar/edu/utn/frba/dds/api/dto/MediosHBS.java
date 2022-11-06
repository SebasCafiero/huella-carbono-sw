package ar.edu.utn.frba.dds.api.dto;

import ar.edu.utn.frba.dds.entities.transportes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MediosHBS {
    private Boolean esPublico = null;
    private String tipo = null;
    private List<TransporteHBS> transportes = null;

    public MediosHBS() {
    }

    public Boolean getEsPublico() {
        return esPublico;
    }

    public String getTipo() {
        return tipo;
    }

    public List<TransporteHBS> getTransportes() {
        return transportes;
    }

    public void setEsPublico(Boolean esPublico) {
        this.esPublico = esPublico;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTransportes(List<TransporteHBS> transportes) {
        this.transportes = transportes;
    }
}