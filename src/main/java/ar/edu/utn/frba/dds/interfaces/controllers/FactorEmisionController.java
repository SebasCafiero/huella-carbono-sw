package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.interfaces.mappers.FactorEmisionMapper;
import ar.edu.utn.frba.dds.interfaces.input.json.FactorEmisionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;

import spark.Request;
import spark.Response;

import java.util.List;

public class FactorEmisionController {
    private Repositorio<FactorEmision> repositorio;

    public FactorEmisionController(){
        this.repositorio = FactoryRepositorio.get(FactorEmision.class);
    }

    public Object modificar(Request request, Response response) {
        FactorEmision factorEmision = FactorEmisionMapper.toEntity(new ParserJSON<>(FactorEmisionJSONDTO.class).parseElement(request.body()));
        this.repositorio.modificar(Integer.parseInt(request.params("id")), factorEmision);
        return "Factor de emision de id : " + request.params("id") + " modificado correctamente.";
    }

    public String mostrarTodos(Request request, Response response) {
        List<FactorEmision> factores = this.repositorio.buscarTodos();
        return factores.toString();
    }

}
