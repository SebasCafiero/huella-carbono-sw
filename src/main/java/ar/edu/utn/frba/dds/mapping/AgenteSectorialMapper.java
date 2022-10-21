package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import java.util.stream.Collectors;

public class AgenteSectorialMapper {
    public static AgenteSectorial toEntity(AgenteSectorialJSONDTO agenteDTO) {
        AgenteSectorial agenteSectorial = new AgenteSectorial(
                AreaSectorialMapper.toEntity(agenteDTO.getArea()),
                ContactoMailMapper.toEntity(agenteDTO.getContactoMail()),
                new ContactoTelefono(agenteDTO.getTelefono()));
        return agenteSectorial;
    }
}
