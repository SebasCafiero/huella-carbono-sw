package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AgenteSectorialMapper {
    public static AgenteSectorial toEntity(AgenteSectorialJSONDTO agenteDTO) {
        AgenteSectorial agenteSectorial = new AgenteSectorial(
                AreaSectorialMapper.toEntity(agenteDTO.area),
                ContactoMailMapper.toEntity(agenteDTO.contactoMail),
                agenteDTO.telefono,
                ReporteMapper.toListOfEntity(agenteDTO.reportes)
        );
        return agenteSectorial;
    }

    public static Set<AgenteSectorial> toSetOfEntity(Set<AgenteSectorialJSONDTO> agentesDTO) {
        HashSet<AgenteSectorial> agentes = new HashSet<>(new ArrayList<AgenteSectorial>());
        //Set<AgenteSectorial> agentes = (Set<AgenteSectorial>) new ArrayList<AgenteSectorial>();
        agentesDTO.forEach(agenteSectorialJSONDTO -> {
            agentes.add(AgenteSectorialMapper.toEntity(agenteSectorialJSONDTO));
        });
        return agentes;
    }
}
