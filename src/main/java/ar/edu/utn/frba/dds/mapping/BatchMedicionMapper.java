package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.MedicionJSONDTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BatchMedicionMapper {

    public static BatchMedicion toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        List<Medicion> mediciones = new ArrayList<>();
         for (MedicionJSONDTO medicionJSONDTO : batchMedicionDTO.mediciones) {
            mediciones.add(MedicionMapper.toEntity(medicionJSONDTO));
        }
        BatchMedicion batchMedicion = new BatchMedicion(mediciones, LocalDate.now());
        return batchMedicion;
    }
}