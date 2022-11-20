package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.BatchMediciones;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.interfaces.input.json.BatchMedicionJSONDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BatchMedicionMapper {
    public static BatchMediciones toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        List<Medicion> collect = batchMedicionDTO.getMediciones().stream()
                .map(MedicionMapper::toEntity).collect(Collectors.toList());

        BatchMediciones batchMediciones = new BatchMediciones(collect);

        return batchMediciones;
    }
}