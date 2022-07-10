package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.util.List;

public class OrganizacionController {
    private Repositorio<Organizacion> repositorio;

    public OrganizacionController(){
        this.repositorio = FactoryRepositorio.get(Organizacion.class);
    }

    public String ejemplo(Request request, Response response){
        return "hola " + request.queryParams("nombre") + " "
                + request.queryParams("apellido") + " con id " + request.params("id");
    }

    public Object eliminar(Request request, Response response){
        Organizacion organizacion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.eliminar(organizacion);
        response.redirect("/mostrarTodos");
        return response;
    }

    public String mostrarTodos(Request request, Response response){
        List<Organizacion> organizaciones = this.repositorio.buscarTodos();
        return organizaciones.toString();
    }
}
