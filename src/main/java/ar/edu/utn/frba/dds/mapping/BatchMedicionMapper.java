package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import org.json.JSONArray;
import org.json.JSONObject;

public class BatchMedicionMapper {
    public static void map(JSONObject batchmedicionDTO, BatchMedicion batchMedicion){
        JSONArray medicionesDTO = batchmedicionDTO.getJSONArray("mediciones");

        //---> ver como hacer con los arrays
        batchMedicion.setId(batchmedicionDTO.optInt("id"));
        //batchMedicion.setFecha(batchmedicionDTO.opt);--->como hacer con el LocalDate?

    }
}
