package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mapping.AgenteSectorialMapper;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.MedicionJSONDTO;
import com.google.gson.Gson;

public class ParserAgentesJSON {
    public static AgenteSectorial generarAgente(String json) {
        AgenteSectorialJSONDTO agenteDTO = new Gson().fromJson(json, AgenteSectorialJSONDTO.class);
        AgenteSectorial unAgenteSectorial = AgenteSectorialMapper.toEntity(agenteDTO);
        return unAgenteSectorial;
    }
}
