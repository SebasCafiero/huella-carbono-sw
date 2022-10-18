package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;
import java.util.Set;

public class AreaSectorialJSONDTO {
    public String nombre;
    public Set<OrganizacionConMiembrosJSONDTO> organizaciones;
    public Set<AgenteSectorialJSONDTO> agentes;
    public List<ReporteJSONDTO> reportes;
}
