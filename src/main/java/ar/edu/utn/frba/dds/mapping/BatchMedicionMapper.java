package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;


public class BatchMedicionMapper {
    public static BatchMedicion toEntity(BatchMedicionJSONDTO batchMedicionDTO) {
        List<Medicion> collect = batchMedicionDTO.mediciones.stream()
                .map(MedicionMapper::toEntity).collect(Collectors.toList());

        BatchMedicion batchMedicion = new BatchMedicion(collect);
        batchMedicion.setOrganizacion(
                FactoryRepositorio.get(Organizacion.class)
                        .buscar(batchMedicionDTO.organizacion)); //El batch es de una organizacion indicada que debe existir previamente

        return batchMedicion;
    }
}