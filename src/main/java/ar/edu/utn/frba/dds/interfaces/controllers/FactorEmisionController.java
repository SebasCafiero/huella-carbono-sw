package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.interfaces.mappers.FactorEmisionMapper;
import ar.edu.utn.frba.dds.interfaces.input.json.FactorEmisionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;

import spark.Request;
import spark.Response;

import java.util.List;

public class FactorEmisionController {
    private final RepoFactores repoFactores;

    public FactorEmisionController(){
        this.repoFactores = (RepoFactores) FactoryRepositorio.get(FactorEmision.class);
    }

    public Object agregar(Request request, Response response) {
        FactorEmision factorEmision = FactorEmisionMapper.toEntity(new ParserJSON<>(FactorEmisionJSONDTO.class).parseElement(request.body()));
        return this.repoFactores.agregar(factorEmision);
    }

    public Object modificar(Request request, Response response) {
        FactorEmision factorEmision = FactorEmisionMapper.toEntity(new ParserJSON<>(FactorEmisionJSONDTO.class).parseElement(request.body()));
        factorEmision.setId(Integer.parseInt(request.params("id")));

        return this.repoFactores.modificar(factorEmision);
    }

    public String mostrarTodos(Request request, Response response) {
        List<FactorEmision> factores = this.repoFactores.buscarTodos();
        return factores.toString();
    }

}
