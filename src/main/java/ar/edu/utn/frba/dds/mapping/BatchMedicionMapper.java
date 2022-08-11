package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BatchMedicionMapper {
    public static void map(JSONObject batchmedicionDTO, BatchMedicion batchMedicion){
        JSONArray medicionesDTO = batchmedicionDTO.getJSONArray("mediciones");

        List<Medicion> mediciones = new ArrayList<>();
        MedicionMapper.map(medicionesDTO, mediciones);
        batchMedicion.setMediciones(mediciones);
        batchMedicion.setId(batchmedicionDTO.optInt("id"));
        batchMedicion.setFecha(batchmedicionDTO.optInt("fecha"));

    }
}