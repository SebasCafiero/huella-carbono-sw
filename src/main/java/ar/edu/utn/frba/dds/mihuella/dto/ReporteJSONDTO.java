package ar.edu.utn.frba.dds.mihuella.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class ReporteJSONDTO {
    public Set<OrganizacionJSONDTO> organizaciones;
    //public Map<OrganizacionJSONDTO,Float> hcOrganizaciones; -->ver como se agrega en el mapper
    public AreaSectorialJSONDTO area;
    public Float hcTotal;
    public LocalDate fecha;
}
