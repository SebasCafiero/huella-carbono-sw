package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.mapping.BatchMedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.BatchMedicionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

public class BatchMedicionController {
    private Repositorio<BatchMedicion> repositorio;

    public BatchMedicionController(){
        this.repositorio = FactoryRepositorio.get(BatchMedicion.class);
    }

    public String mostrarTodos(Request request, Response response) {
        List<BatchMedicion> batches = this.repositorio.buscarTodos();
        return batches.toString();
    }

    public String obtener(Request request, Response response){
        BatchMedicion batchMedicion = this.repositorio.buscar(Integer.parseInt(request.params("id")));
        //return batchMedicion.toString();
        String json = new Gson().toJson(batchMedicion);
        return json;
    }

    public Object agregar(Request request, Response response){
        BatchMedicion batchMedicion = BatchMedicionMapper.toEntity(
                new ParserJSON<>(BatchMedicionJSONDTO.class).parseElement(request.body()));
        batchMedicion.setFecha(LocalDate.now());
        this.repositorio.agregar(batchMedicion);
        for(Medicion medicion : batchMedicion.getMediciones()) {
            FactoryRepositorio.get(Medicion.class).agregar(medicion);
            batchMedicion.getOrganizacion().agregarMediciones(medicion); //Como ahora el batch es de una org, le agrego sus mediciones
        }
        return "BatchMedicion agregado correctamente.";
    }

    public Object eliminar(Request request, Response response){
        BatchMedicion batchMedicion = this.repositorio.buscar(Integer.parseInt(request.params("id")));
        this.repositorio.eliminar(batchMedicion);
        return "BatchMedicion de id : " + request.params("id") + " eliminado correctamente.";
    }

}