package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.mediciones.Categoria;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONString;

import java.util.List;


public class MedicionMapper {
    public static void map(JSONObject medicionDTO, Medicion medicion){
        JSONObject categoriaDTO = medicionDTO.optJSONObject("categoria");
        Categoria categoria = new Categoria(
                categoriaDTO.optInt("id"),
                categoriaDTO.optString("actividad"),
                categoriaDTO.optString("tipoConsumo")
        );


        medicion.setCategoria(categoria);
        medicion.setUnidad(medicionDTO.optString("unidad"));
        medicion.setPeriodicidad(medicionDTO.optString("periodicidad"));
        medicion.setValor(medicionDTO.optFloat("valor"));
        medicion.setPeriodo(medicionDTO.optString("periodo"));
        medicion.setFecha(medicionDTO.optInt("fecha"));
    }

    public static void map(JSONArray medicionesDTO, List<Medicion> mediciones) {
        medicionesDTO.forEach(itemMedicion -> {
            JSONObject medicionDTO = (JSONObject) itemMedicion;

            Medicion medicion = new Medicion();
            MedicionMapper.map(medicionDTO,medicion);
            mediciones.add(medicion);
    });
    }
}
