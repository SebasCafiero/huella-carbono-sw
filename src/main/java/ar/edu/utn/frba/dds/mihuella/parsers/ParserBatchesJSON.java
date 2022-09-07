package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mapping.MedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ParserBatchesJSON {
    public static BatchMedicion generarBatch(String json) {

        System.out.println("Request: "+json);
        BatchMedicionJSONDTO batchDTO = new Gson().fromJson(json, BatchMedicionJSONDTO.class);
        List<Medicion> mediciones = MedicionMapper.toListOfEntity(batchDTO.mediciones);
        return new BatchMedicion(mediciones, LocalDate.parse(batchDTO.fecha));
    }
}
