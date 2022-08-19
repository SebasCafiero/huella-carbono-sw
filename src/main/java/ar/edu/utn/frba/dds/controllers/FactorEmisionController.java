package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import spark.Request;
import spark.Response;

public class FactorEmisionController {
    private Repositorio<FactorEmision> repositorio;

    public FactorEmisionController(){
        this.repositorio = FactoryRepositorio.get(FactorEmision.class);
    }

    public Object modificar(Request request, Response response){
        FactorEmision factorEmision = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        this.repositorio.modificar(factorEmision);
        return response;
    }

}
