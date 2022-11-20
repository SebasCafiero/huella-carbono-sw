package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
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

    public static Medicion toEntity(MedicionJSONDTO dto) {
        Periodo periodo;
        if(dto.getPeriodo().getPeriodicidad() == 'M') {
            periodo = new Periodo(dto.getPeriodo().getAnio(), dto.getPeriodo().getMes());
        } else {
            periodo = new Periodo(dto.getPeriodo().getAnio());
        }

        return new Medicion(
                new Categoria(dto.getCategoria().getActividad(),
                        dto.getCategoria().getTipoConsumo()),
                dto.getUnidad(), dto.getValor(), periodo);
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
