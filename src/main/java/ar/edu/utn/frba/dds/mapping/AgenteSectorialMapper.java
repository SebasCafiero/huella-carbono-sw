package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Reporte;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.ReporteJSONDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgenteSectorialMapper {
    public static AgenteSectorial toEntity(AgenteSectorialJSONDTO agenteDTO) {
        List<Reporte> reportes = new ArrayList<>();
        for (ReporteJSONDTO reporteJSONDTO : agenteDTO.reportes) {
            reportes.add(ReporteMapper.toEntity(reporteJSONDTO));
        }
//        reportes = agenteDTO.reportes.stream().map(ReporteMapper::toEntity).collect(Collectors.toList());

        AgenteSectorial agenteSectorial = new AgenteSectorial(
                AreaSectorialMapper.toEntity(agenteDTO.area),
                ContactoMailMapper.toEntity(agenteDTO.contactoMail),
                agenteDTO.telefono,
                reportes
        );
        return agenteSectorial;
    }
}
