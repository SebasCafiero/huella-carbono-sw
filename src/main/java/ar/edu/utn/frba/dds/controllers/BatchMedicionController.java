package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.mapping.BatchMedicionMapper;
import ar.edu.utn.frba.dds.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import java.util.List;
import ar.edu.utn.frba.dds.repositories.Repositorio;
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
        return batchMedicion.getFecha().toString();
    }

    public Object agregar(Request request, Response response){
        JSONObject jsonObject = new JSONObject(request.body());
        BatchMedicion batchMedicion = new BatchMedicion();
        BatchMedicionMapper.map(jsonObject, batchMedicion);
        this.repositorio.agregar(batchMedicion);
        return response;
    }

    public Object modificar(Request request, Response response){
        BatchMedicion batchMedicion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.modificar(batchMedicion);
        return response;
    }

    public Object eliminar(Request request, Response response){
        BatchMedicion batchMedicion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.eliminar(batchMedicion);
        return response;
    }

}
/*
public String obtener(Request request, Response response){
        Organizacion organizacion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        return organizacion.getRazonSocial();
    }

    public Object eliminar(Request request, Response response){
        Organizacion organizacion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.eliminar(organizacion);
        return response;
    }

    public Object agregar(Request request, Response response){
        JSONObject jsonObject = new JSONObject(request.body());
        Organizacion organizacion = new Organizacion();
        OrganizacionMapper.map(jsonObject, organizacion);
        this.repositorio.agregar(organizacion);
        return response;
    }

    public String mostrarTodos(Request request, Response response){
        List<Organizacion> organizaciones = this.repositorio.buscarTodos();
        return organizaciones.toString();
    }
 */