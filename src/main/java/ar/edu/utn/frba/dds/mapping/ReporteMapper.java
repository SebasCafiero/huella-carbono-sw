package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.Reporte;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.ReporteJSONDTO;

import java.util.HashSet;
import java.util.Set;


public class ReporteMapper {
    public static Reporte toEntity(ReporteJSONDTO reporteDTO) {
        Set<Organizacion> organizaciones = new HashSet<>();
        for (OrganizacionJSONDTO organizacionJSONDTO : reporteDTO.organizaciones) {
            organizaciones.add(OrganizacionMapper.toEntity(organizacionJSONDTO));
        }
        Reporte reporte = new Reporte(
                organizaciones,
                //ver como agregar el map
                AreaSectorialMapper.toEntity(reporteDTO.area),
                reporteDTO.hcTotal
        );
        return reporte;
    }
}
