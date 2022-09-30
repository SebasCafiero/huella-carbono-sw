package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import com.google.gson.Gson;

public class ParserAgentesJSON {
    public static AgenteSectorialJSONDTO generarAgente(String json) {
        return new Gson().fromJson(json, AgenteSectorialJSONDTO.class);
    }
}
