package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.mihuella.dto.MedicionCSVDTO;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserTrayectos;
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

        JSONObject periodoDTO = medicionDTO.optJSONObject("periodo");
        Periodo periodo;
        if(Character.valueOf('M').equals(periodoDTO.optString("periodicidad").trim().charAt(0))) {
            periodo = new Periodo(medicionDTO.optInt("anio"), medicionDTO.optInt("mes"));
        } else {
            periodo = new Periodo(medicionDTO.optInt("anio"));
        }

        medicion.setPeriodo(periodo);
        medicion.setCategoria(categoria);
        medicion.setUnidad(medicionDTO.optString("unidad"));
        medicion.setValor(medicionDTO.optFloat("valor"));

        return medicion;
    }

    public static Medicion toEntity(MedicionCSVDTO medicionCSVDTO) {
        Categoria categoria = new Categoria(medicionCSVDTO.getActividad().trim(), medicionCSVDTO.getTipoConsumo().trim());
        /*Medicion medicion = new Medicion(categoria, medicionCSVDTO.getUnidad().trim(),
                Float.parseFloat(medicionCSVDTO.getDatoActividad().trim()),
                medicionCSVDTO.getPeriodicidad().trim().charAt(0),
                PeriodoMapper.toLocalDate(
                        medicionCSVDTO.getPeriodicidad().trim().charAt(0),
                        medicionCSVDTO.getPeriodo().trim()));*/
        return new Medicion(
                categoria,
                medicionCSVDTO.getUnidad().trim(),
                Float.parseFloat(medicionCSVDTO.getDatoActividad().trim()),
                PeriodoMapper.toEntity(
                        medicionCSVDTO.getPeriodicidad().trim().charAt(0),
                        medicionCSVDTO.getPeriodo().trim()
                )
        );
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
