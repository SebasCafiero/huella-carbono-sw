package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;

import java.util.stream.Collectors;


public class BatchMedicionMapper {

    public static BatchMedicion toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        BatchMedicion batchMedicion = new BatchMedicion(batchMedicionDTO.mediciones.stream().map(MedicionMapper::toEntity).collect(Collectors.toList()));
        return batchMedicion;
    }
}