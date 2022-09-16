package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserBatchesJSON;
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
        BatchMedicion batchMedicion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        //return batchMedicion.toString();
        String json = new Gson().toJson(batchMedicion);
        return json;
    }


    public Object agregar(Request request, Response response){
        BatchMedicion batchMedicion = ParserBatchesJSON.generarBatch(request.body());
        batchMedicion.setFecha(LocalDate.now());
        this.repositorio.agregar(batchMedicion);
        return "BatchMedicion agregado correctamente.";
    }


    public Object eliminar(Request request, Response response){
        BatchMedicion batchMedicion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.eliminar(batchMedicion);
        return "BatchMedicion de id : " + request.params("id") + " eliminado correctamente.";
    }

}