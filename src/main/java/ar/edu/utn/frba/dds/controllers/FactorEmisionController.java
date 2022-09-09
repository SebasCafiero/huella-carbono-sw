package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserFactoresJSON;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import spark.Request;
import spark.Response;

import java.util.List;

public class FactorEmisionController {
    private Repositorio<FactorEmision> repositorio;

    public FactorEmisionController(){
        this.repositorio = FactoryRepositorio.get(FactorEmision.class);
    }

    public Object modificar(Request request, Response response){
        FactorEmision factorEmision = ParserFactoresJSON.generarFactor(request.body());
        this.repositorio.modificar(Integer.valueOf(request.params("id")), factorEmision);
        return response;
    }

    public String mostrarTodos(Request request, Response response) { // solo para probar
        List<FactorEmision> factores = this.repositorio.buscarTodos();
        return factores.toString();
    }

}
