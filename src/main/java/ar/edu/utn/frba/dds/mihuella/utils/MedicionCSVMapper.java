package ar.edu.utn.frba.dds.mihuella.utils;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.mihuella.MedicionCSVDTO;

public class MedicionCSVMapper {
    public static Medicion toEntity(MedicionCSVDTO medicionCSVDTO) {
        Categoria categoria = new Categoria(medicionCSVDTO.getActividad().trim(), medicionCSVDTO.getTipoConsumo().trim());
        Periodo periodo = PeriodoMapper.toEntity(medicionCSVDTO.getPeriodicidad().trim().charAt(0),
                medicionCSVDTO.getPeriodo().trim());
        Medicion medicion = new Medicion(categoria, medicionCSVDTO.getUnidad().trim(),
                Float.parseFloat(medicionCSVDTO.getDatoActividad().trim()),
                periodo);

        return medicion;
    }
}
