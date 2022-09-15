package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mapping.BatchMedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import com.google.gson.Gson;

public class ParserBatchesJSON {
    public static BatchMedicion generarBatch(String json) {

        System.out.println("Request: "+json);
        BatchMedicionJSONDTO batchDTO = new Gson().fromJson(json, BatchMedicionJSONDTO.class);
        BatchMedicion batchMedicion = BatchMedicionMapper.toEntity(batchDTO);
        return batchMedicion;
    }
}
