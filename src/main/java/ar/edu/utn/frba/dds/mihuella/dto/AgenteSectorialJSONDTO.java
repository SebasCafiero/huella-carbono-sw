package ar.edu.utn.frba.dds.mihuella.dto;

import ar.edu.utn.frba.dds.entities.personas.ContactoMail;

import java.util.List;

public class AgenteSectorialJSONDTO {
    public AreaSectorialJSONDTO area;
    public ContactoMailJSONDTO contactoMail;
    public String telefono;
    public List<ReporteJSONDTO> reportes;
}
