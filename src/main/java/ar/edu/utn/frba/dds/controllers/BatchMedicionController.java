package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mapping.BatchMedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.ErrorResponse;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

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

    public String obtener(Request request, Response response) {
        BatchMedicion batchMedicion = this.repoBatch.buscar(Integer.parseInt(request.params("id")));
        //return batchMedicion.toString();
        String json = new Gson().toJson(batchMedicion);
        return json;
    }

    public Object agregar(Request request, Response response) {
        BatchMedicionJSONDTO requestDTO = new ParserJSON<>(BatchMedicionJSONDTO.class).parseElement(request.body());

        BatchMedicion batchMedicion = BatchMedicionMapper.toEntity(requestDTO);
        batchMedicion.setFecha(LocalDate.now());

        Organizacion organizacion = this.repoOrganizaciones.buscar(requestDTO.getOrganizacion());

        if(organizacion == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + request.params("id") + " no existe");
        }
        batchMedicion.setOrganizacion(organizacion);

        this.repoBatch.agregar(batchMedicion);
        for(Medicion medicion : batchMedicion.getMediciones()) {
            FactoryRepositorio.get(Medicion.class).agregar(medicion);
            batchMedicion.getOrganizacion().agregarMediciones(medicion); //Como ahora el batch es de una org, le agrego sus mediciones
        }
        return "BatchMedicion agregado correctamente.";
    }

    public Object eliminar(Request request, Response response) {
        BatchMedicion batchMedicion = this.repoBatch.buscar(Integer.parseInt(request.params("id")));
        this.repoBatch.eliminar(batchMedicion);
        return "BatchMedicion de id : " + request.params("id") + " eliminado correctamente.";
    }

}