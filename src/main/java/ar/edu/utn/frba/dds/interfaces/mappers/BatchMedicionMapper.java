package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.BatchMediciones;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.interfaces.input.json.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.BatchMedicionesResponse;

import java.util.List;
import java.util.stream.Collectors;

public class BatchMedicionMapper {
    public static BatchMediciones toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        List<Medicion> collect = batchMedicionDTO.getMediciones().stream()
                .map(MedicionMapper::toEntity).collect(Collectors.toList());

        BatchMediciones batchMediciones = new BatchMediciones(collect);

        return batchMediciones;
    }

    public static BatchMedicionesResponse toDTO(BatchMediciones entity) {
        BatchMedicionesResponse dto = new BatchMedicionesResponse();
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidadMediciones());
        dto.setFecha(entity.getFecha());
        dto.setMediciones(entity.getMediciones().stream().map(MedicionMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }

    public static BatchMedicionesResponse toDTOLazy(BatchMediciones entity) {
        BatchMedicionesResponse dto = new BatchMedicionesResponse();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setCantidad(entity.getCantidadMediciones());
        return dto;
    }
}