package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BatchMedicionMapper {
    public static BatchMedicion toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        List<Medicion> collect = batchMedicionDTO.getMediciones().stream()
                .map(MedicionMapper::toEntity).collect(Collectors.toList());

        BatchMedicion batchMedicion = new BatchMedicion(collect);

        return batchMedicion;
    }
}