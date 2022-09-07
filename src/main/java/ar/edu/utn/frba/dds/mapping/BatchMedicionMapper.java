package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;

public class BatchMedicionMapper {
    public static BatchMedicion toEntity(JSONObject batchmedicionDTO){
        BatchMedicion batchMedicion = new BatchMedicion();
        JSONArray medicionesDTO = batchmedicionDTO.getJSONArray("mediciones");

        batchMedicion.setMediciones(MedicionMapper.toListOfEntity(medicionesDTO));
        batchMedicion.setId(batchmedicionDTO.optInt("id"));
        batchMedicion.setFecha(batchmedicionDTO.optString("fecha"));

        return batchMedicion;
    }

    public static BatchMedicion toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        BatchMedicion batchMedicion = new BatchMedicion(
                MedicionMapper.toListOfEntity(batchMedicionDTO.mediciones),
                LocalDate.parse(batchMedicionDTO.fecha));
        return batchMedicion;
    }
}