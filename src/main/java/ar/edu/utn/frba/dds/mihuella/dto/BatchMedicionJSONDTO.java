package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;

public class BatchMedicionJSONDTO {
    private Integer organizacion; //id de la organizacion
    private List<MedicionJSONDTO> mediciones;

    public Integer getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Integer organizacion) {
        this.organizacion = organizacion;
    }

    public List<MedicionJSONDTO> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<MedicionJSONDTO> mediciones) {
        this.mediciones = mediciones;
    }
}
