package ar.edu.utn.frba.dds.interfaces.input.json;

import java.util.List;

public class BatchMedicionJSONDTO {
    private List<MedicionJSONDTO> mediciones;

    public List<MedicionJSONDTO> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<MedicionJSONDTO> mediciones) {
        this.mediciones = mediciones;
    }
}
