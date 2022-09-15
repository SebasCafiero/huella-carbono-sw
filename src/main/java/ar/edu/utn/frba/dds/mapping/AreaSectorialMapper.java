package ar.edu.utn.frba.dds.mapping;


import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.Reporte;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.AreaSectorialJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.ReporteJSONDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AreaSectorialMapper {
    public static AreaSectorial toEntity(AreaSectorialJSONDTO areaDTO) {
        AreaSectorial area = new AreaSectorial() {};
        area.setNombre(areaDTO.nombre);
        Set<Organizacion> organizaciones = new HashSet<>();
        for (OrganizacionJSONDTO organizacionJSONDTO : areaDTO.organizaciones) {
            organizaciones.add(OrganizacionMapper.toEntity(organizacionJSONDTO));
        }
        area.setOrganizaciones(organizaciones);
        Set<AgenteSectorial> agentes = new HashSet<>();
        for (AgenteSectorialJSONDTO agenteSectorialJSONDTO : areaDTO.agentes) {
            agentes.add(AgenteSectorialMapper.toEntity(agenteSectorialJSONDTO));
        }
        area.setAgentes(agentes);
        List<Reporte> reportes = new ArrayList<>();
        for (ReporteJSONDTO reporteJSONDTO : areaDTO.reportes) {
            reportes.add(ReporteMapper.toEntity(reporteJSONDTO));
        }
        area.setReportes(reportes);
        return area;
    }
}
