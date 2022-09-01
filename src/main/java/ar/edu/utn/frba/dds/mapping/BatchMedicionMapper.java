package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import org.json.JSONArray;
import org.json.JSONObject;

public class BatchMedicionMapper {
    public static BatchMedicion toEntity(JSONObject batchmedicionDTO){
        BatchMedicion batchMedicion = new BatchMedicion();
        JSONArray medicionesDTO = batchmedicionDTO.getJSONArray("mediciones");

        batchMedicion.setMediciones(MedicionMapper.toListOfEntity(medicionesDTO));
        batchMedicion.setId(batchmedicionDTO.optInt("id"));
        batchMedicion.setFecha(batchmedicionDTO.optString("fecha"));

        return batchMedicion;
    }
}