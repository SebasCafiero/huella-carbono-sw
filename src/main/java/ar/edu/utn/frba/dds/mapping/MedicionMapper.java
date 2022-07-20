package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.mediciones.Categoria;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONString;


public class MedicionMapper {
    public static void map(JSONObject medicionDTO, Medicion medicion){
        JSONObject categoriaDTO = medicionDTO.optJSONObject("categoria");
        Categoria categoria = new Categoria(
                //categoriaDTO.optInt("id"), --> como hacer con el integer?
                categoriaDTO.optString("actividad"),
                categoriaDTO.optString("tipoConsumo")
        );


        medicion.setCategoria(categoria);
        medicion.setUnidad(medicionDTO.optString("unidad"));
        //medicion.setPeriodicidad(medicionDTO.opt); ---> como hacer con el Character?
        medicion.setValor(medicionDTO.optFloat("valor"));
        medicion.setPeriodo(medicionDTO.optString("periodo"));
        //medicion.setFecha(medicionDTO.opt); --->como hacer con el LocalDate?
    }
}
