package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.medibles.BatchMedicion;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.interfaces.mappers.BatchMedicionMapper;
import ar.edu.utn.frba.dds.interfaces.input.json.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
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

    public Object obtener(Request request, Response response){
        BatchMedicion batchMedicion = this.repoBatch.buscar(Integer.parseInt(request.params("id")));
        if(batchMedicion == null) {
            throw new EntityNotFoundException();
        }
        
        return batchMedicion;
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
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        BatchMedicion batchMedicion = this.repoBatch.buscar(Integer.parseInt(request.params("id")));
        this.repoBatch.eliminar(batchMedicion);

        return "BatchMedicion de id : " + request.params("id") + " eliminado correctamente.";
    }

}