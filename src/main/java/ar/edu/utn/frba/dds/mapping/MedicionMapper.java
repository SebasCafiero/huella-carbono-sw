package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicionMapper {
    public static Medicion toEntity(JSONObject medicionDTO) {
        Medicion medicion = new Medicion();
        JSONObject categoriaDTO = medicionDTO.optJSONObject("categoria");
        Categoria categoria = new Categoria(
                categoriaDTO.optInt("id"),
                categoriaDTO.optString("actividad"),
                categoriaDTO.optString("tipoConsumo")
        );

        medicion.setCategoria(categoria);
        medicion.setUnidad(medicionDTO.optString("unidad"));
        medicion.setPeriodicidad(medicionDTO.optString("periodicidad").trim().charAt(0));
        medicion.setValor(medicionDTO.optFloat("valor"));
        medicion.setFecha(LocalDate.parse(medicionDTO.optString("fecha")));

        return medicion;
    }

    public static List<Medicion> toListOfEntity(JSONArray medicionesDTO) {
        List<Medicion> mediciones = new ArrayList<>();
        medicionesDTO.forEach(itemMedicion -> {
            JSONObject medicionDTO = (JSONObject) itemMedicion;
            mediciones.add(MedicionMapper.toEntity(medicionDTO));
        });

        return mediciones;
    }
}
