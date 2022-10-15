package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import java.time.LocalDate;
import java.util.stream.Collectors;


public class BatchMedicionMapper {

    public static BatchMedicion toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        BatchMedicion batchMedicion = new BatchMedicion(batchMedicionDTO
                .mediciones
                .stream()
                .map(MedicionMapper::toEntity)
                .collect(Collectors.toList()),
                LocalDate.now()); //seteo fecha actual
        batchMedicion.setOrganizacion(
                FactoryRepositorio.get(Organizacion.class)
                .buscar(batchMedicionDTO.organizacion));
        return batchMedicion;
    }
}