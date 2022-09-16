package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Reporte;
import ar.edu.utn.frba.dds.mihuella.dto.ReporteJSONDTO;

import java.util.stream.Collectors;


public class ReporteMapper {
    public static Reporte toEntity(ReporteJSONDTO reporteDTO) {
        Reporte reporte = new Reporte(
                reporteDTO.organizaciones.stream().map(OrganizacionMapper::toEntity).collect(Collectors.toSet()),
                //ver como agregar el map
                AreaSectorialMapper.toEntity(reporteDTO.area),
                reporteDTO.hcTotal
        );
        return reporte;
    }
}
