package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import java.util.stream.Collectors;

public class AgenteSectorialMapper {
    public static AgenteSectorial toEntity(AgenteSectorialJSONDTO agenteDTO) {
        AgenteSectorial agenteSectorial = new AgenteSectorial(
                AreaSectorialMapper.toEntity(agenteDTO.area),
                ContactoMailMapper.toEntity(agenteDTO.contactoMail),
                agenteDTO.telefono);
        return agenteSectorial;
    }
}
