package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserOrganizacionesJSON;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.List;

public class OrganizacionController {
    private Repositorio<Organizacion> repositorio;

    public OrganizacionController(){
        this.repositorio = FactoryRepositorio.get(Organizacion.class);
    }

    public String obtener(Request request, Response response){
        Organizacion organizacion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        return organizacion.getRazonSocial();
    }

    public Object eliminar(Request request, Response response){
        Organizacion organizacion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.eliminar(organizacion);
        return response;
    }

    public Object modificar(Request request, Response response){
        Organizacion organizacion = ParserOrganizacionesJSON.generarOrganizacion(request.body());
        this.repositorio.modificar(Integer.valueOf(request.params("id")), organizacion);
        return response;
    }

    public Object agregar(Request request, Response response){
        Organizacion organizacion = ParserOrganizacionesJSON.generarOrganizacion(request.body());
        this.repositorio.agregar(organizacion);
        return response;
    }

    public String mostrarTodos(Request request, Response response){
        List<Organizacion> organizaciones = this.repositorio.buscarTodos();
        return organizaciones.toString();
    }
}
