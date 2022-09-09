package ar.edu.utn.frba.dds.mapping;


import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.mihuella.dto.AreaSectorialJSONDTO;


public class AreaSectorialMapper {
    public static AreaSectorial toEntity(AreaSectorialJSONDTO areaDTO) {
        AreaSectorial area = new AreaSectorial() {};
        area.setNombre(areaDTO.nombre);
        area.setOrganizaciones(OrganizacionMapper.toSetOfEntity(areaDTO.organizaciones));
        area.setAgentes(AgenteSectorialMapper.toSetOfEntity(areaDTO.agentes));
        area.setReportes(ReporteMapper.toListOfEntity(areaDTO.reportes));
        return area;
    }
}
