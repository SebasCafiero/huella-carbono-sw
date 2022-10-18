package ar.edu.utn.frba.dds.mihuella.dto;

import java.time.LocalDate;
import java.util.List;

public class BatchMedicionJSONDTO {
    public Integer organizacion; //id de la organizacion
    public List<MedicionJSONDTO> mediciones;
    public String fecha; //se atrapa pero se setea luego el LocalDate.now
}
