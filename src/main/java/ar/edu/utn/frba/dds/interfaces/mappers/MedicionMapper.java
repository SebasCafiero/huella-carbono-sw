package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.interfaces.input.csv.MedicionCSVDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.CategoriaJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.MedicionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.PeriodoJSONDTO;


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
                new Categoria(medicionDTO.getCategoria().getActividad(), medicionDTO.getCategoria().getTipoConsumo()),
                medicionDTO.getUnidad(),
                medicionDTO.getValor()
        );
        return medicion;
    }

    public static MedicionJSONDTO toDTO(Medicion entity) {
        MedicionJSONDTO dto = new MedicionJSONDTO();
        dto.setCategoria(new CategoriaJSONDTO(entity.getMiCategoria().getActividad(), entity.getMiCategoria().getTipoConsumo()));
        dto.setPeriodo(new PeriodoJSONDTO(entity.getPeriodo().getAnio(), entity.getPeriodo().getMes()));
        dto.setUnidad(entity.getUnidad());
        dto.setValor(entity.getValor());

        return dto;
    }
}
