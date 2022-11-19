package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.medibles.BatchMedicion;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.interfaces.mappers.BatchMedicionMapper;
import ar.edu.utn.frba.dds.interfaces.input.json.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.interfaces.mappers.MedicionMapper;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;

import javax.persistence.EntityNotFoundException;

public class BatchMedicionController {
    private Repositorio<BatchMedicion> repoBatch;
    private final Repositorio<Organizacion> repoOrganizaciones;

    public BatchMedicionController(){
        this.repoBatch = FactoryRepositorio.get(BatchMedicion.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
    }

    public String mostrarTodos(Request request, Response response) {
        List<BatchMedicion> batches = this.repoBatch.buscarTodos();
        return batches.toString();
    }

    public BatchMedicion obtener(Request request, Response response){
        Optional<BatchMedicion> batchMedicion = this.repoBatch.buscar(Integer.parseInt(request.params("batch")));
        if (!batchMedicion.isPresent() || !batchMedicion.get().getOrganizacion().getId()
                .equals(Integer.parseInt(request.params("id")))) {
            throw new MiHuellaApiException("El batch " + request.params("batch") + " de la organizacion "
                    + request.params("id") + " no existe");
        }
        return batchMedicion.get();
    }

    public Object agregar(Request request, Response response) {
        BatchMedicionJSONDTO requestDTO = new ParserJSON<>(BatchMedicionJSONDTO.class).parseElement(request.body());
        List<Medicion> mediciones = requestDTO.getMediciones().stream().map(MedicionMapper::toEntity).collect(Collectors.toList());

        // El get no puede romper porque se sabe de la existencia de la organización por los controles de seguridad
        Organizacion organizacion = this.repoOrganizaciones.buscar(requestDTO.getOrganizacion()).get();
        organizacion.agregarMediciones(mediciones);

        BatchMedicion batchMedicion = new BatchMedicion(mediciones);
        batchMedicion.setFecha(LocalDate.now());
        batchMedicion.setOrganizacion(organizacion);

        this.repoBatch.agregar(batchMedicion);

        response.status(201);
        return "BatchMedicion agregado correctamente.";
    }

    public Object eliminar(Request request, Response response) {
        Optional<BatchMedicion> batchMedicion = this.repoBatch.buscar(Integer.parseInt(request.params("id")));
        if (!batchMedicion.isPresent() || !batchMedicion.get().getOrganizacion().getId()
                .equals(Integer.parseInt(request.params("id")))) {
            throw new MiHuellaApiException("El batch " + request.params("batch") + " de la organizacion "
                    + request.params("id") + " no existe");
        }
        this.repoBatch.eliminar(batchMedicion.get());

        return "BatchMedicion de id : " + request.params("id") + " eliminado correctamente.";
    }

}