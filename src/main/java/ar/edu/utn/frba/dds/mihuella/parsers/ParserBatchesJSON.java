package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mapping.BatchMedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserBatchesJSON {
    public static List<BatchMedicion> generarBatches(String archivo) throws IOException {
        List<BatchMedicion> batches = new ArrayList<>();

        Type listType = new TypeToken<List<BatchMedicionJSONDTO>>(){}.getType();
        List<BatchMedicionJSONDTO> batchesDTO = new Gson().fromJson(new FileReader(archivo), listType);

        for(BatchMedicionJSONDTO batchDTO : batchesDTO){
            BatchMedicion unBatch = BatchMedicionMapper.toEntity(batchDTO);
            batches.add(unBatch);
        }
        return batches;
    }
}
