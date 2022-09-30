package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionJSONDTO;
import com.google.gson.Gson;


public class ParserFactoresJSON {
    public static FactorEmisionJSONDTO generarFactor(String json) {
        System.out.println("Request: "+json);
        return new Gson().fromJson(json, FactorEmisionJSONDTO.class);
    }
}
