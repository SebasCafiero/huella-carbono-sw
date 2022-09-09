package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Reporte;
import ar.edu.utn.frba.dds.mihuella.dto.ReporteJSONDTO;

import java.util.ArrayList;
import java.util.List;

public class ReporteMapper {
    public static Reporte toEntity(ReporteJSONDTO reporteDTO) {
        Reporte reporte = new Reporte(
                OrganizacionMapper.toSetOfEntity(reporteDTO.organizaciones),
                //ver como agregar el map
                AreaSectorialMapper.toEntity(reporteDTO.area),
                reporteDTO.hcTotal
        );
        return reporte;
    }

    public static List<Reporte> toListOfEntity(List<ReporteJSONDTO> reportesDTO) {
        List<Reporte> reportes = new ArrayList<>();
        reportesDTO.forEach(reporteJSONDTO -> {
            reportes.add(ReporteMapper.toEntity(reporteJSONDTO));
        });
        return reportes;
    }
}
