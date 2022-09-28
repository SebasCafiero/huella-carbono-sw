package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.mihuella.dto.AreaSectorialJSONDTO;

import java.util.stream.Collectors;

public class AreaSectorialMapper {
    public static AreaSectorial toEntity(AreaSectorialJSONDTO areaDTO) {
        AreaSectorial area = new AreaSectorial() {};
        area.setNombre(areaDTO.nombre);
        area.setOrganizaciones(areaDTO.organizaciones.stream().map(OrganizacionMapper::toEntity).collect(Collectors.toSet()));
//        area.setAgentes(areaDTO.agentes.stream().map(AgenteSectorialMapper::toEntity).collect(Collectors.toSet()));
//        area.setReportes(areaDTO.reportes.stream().map(ReporteMapper::toEntity).collect(Collectors.toList()));
        return area;
    }
}
