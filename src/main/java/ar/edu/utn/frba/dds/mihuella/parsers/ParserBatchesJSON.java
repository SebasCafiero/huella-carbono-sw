package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import com.google.gson.Gson;

public class ParserBatchesJSON {
    public static BatchMedicionJSONDTO generarBatch(String json) {
        System.out.println("Request: "+json);
        return new Gson().fromJson(json, BatchMedicionJSONDTO.class);
    }
}
