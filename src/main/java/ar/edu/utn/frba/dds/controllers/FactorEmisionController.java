package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.login.LoginController;
import ar.edu.utn.frba.dds.mapping.FactorEmisionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import spark.Request;
import spark.Response;

import java.util.List;

public class FactorEmisionController {
    private Repositorio<FactorEmision> repositorio;
    private LoginController loginController;

    public FactorEmisionController(){
        this.repositorio = FactoryRepositorio.get(FactorEmision.class);
        loginController = new LoginController();
    }

    public Object modificar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        FactorEmision factorEmision = FactorEmisionMapper.toEntity(new ParserJSON<>(FactorEmisionJSONDTO.class).parseElement(request.body()));
        this.repositorio.modificar(Integer.parseInt(request.params("id")), factorEmision);
        return "Factor de emision de id : " + request.params("id") + " modificado correctamente.";
    }

    public String mostrarTodos(Request request, Response response) { // solo para probar
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        List<FactorEmision> factores = this.repositorio.buscarTodos();
        return factores.toString();
    }

}
