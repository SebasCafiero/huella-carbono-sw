package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.interfaces.input.csv.MedicionCSVDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.MedicionJSONDTO;


public class MedicionMapper {

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

    public static Medicion toEntity(MedicionJSONDTO medicionDTO) {
        Medicion medicion = new Medicion(
                CategoriaMapper.toEntity(medicionDTO.categoria),
                medicionDTO.unidad,
                medicionDTO.valor
        );
        return medicion;
    }
}
