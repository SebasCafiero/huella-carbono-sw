package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.interfaces.gui.dto.AgenteHBS;

import java.util.stream.Collectors;

public class AgenteMapperHBS {
    public static AgenteHBS toDTO(AgenteSectorial agente) {
        AgenteHBS agenteDTO = new AgenteHBS();
        agenteDTO.setId(agente.getId());
        agenteDTO.setOrganizaciones(agente
                        .getArea()
                        .getOrganizaciones()
                        .stream()
                        .map(OrganizacionMapperHBS::toDTO)
                        .collect(Collectors.toList()));
        return agenteDTO;
    }
}
