package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.mediciones.batchMedicion;
import spark.Request;
import spark.Response;
import java.util.List;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

public class batchMedicionController {
    public String mostrarTodos(Request request, Response response) {
       //List<batchMedicion> batches = this.repositorio.buscarTodos();
        return "hola";
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