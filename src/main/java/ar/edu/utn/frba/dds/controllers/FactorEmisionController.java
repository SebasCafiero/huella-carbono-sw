package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mapping.FactorEmisionMapper;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.List;

public class FactorEmisionController {
    private Repositorio<FactorEmision> repositorio;

    public FactorEmisionController(){
        this.repositorio = FactoryRepositorio.get(FactorEmision.class);
    }

    public Object modificar(Request request, Response response){
        FactorEmision factorEmisionActual = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        FactorEmision factorEmisionNuevo = new FactorEmision();
        JSONObject jsonObject = new JSONObject(request.body());
//        FactorEmisionMapper.toEntity(jsonObject);
//        this.repositorio.modificar(factorEmisionActual,factorEmisionNuevo);
        List<FactorEmision> factores = this.repositorio.buscarTodos();
        return factores.toString();
    }

    public String mostrarTodos(Request request, Response response) { // solo para probar
        List<FactorEmision> factores = this.repositorio.buscarTodos();
        return factores.toString();
    }

}
