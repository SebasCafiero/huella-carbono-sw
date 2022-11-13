package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mapping.AgenteSectorialMapper;
import ar.edu.utn.frba.dds.mihuella.dto.AgenteSectorialJSONDTO;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.Request;
import spark.Response;

import java.util.List;

public class AgenteSectorialController {
    private Repositorio<AgenteSectorial> repositorio;
    private LoginController loginController;

    public AgenteSectorialController() {this.repositorio = FactoryRepositorio.get(AgenteSectorial.class);
    loginController = new LoginController();
    }

    public String mostrarTodos(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        List<AgenteSectorial> agentes =this.repositorio.buscarTodos();
        return agentes.toString();
    }

    public String obtener(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = this.repositorio.buscar(Integer.parseInt(request.params("id")));
        return agenteSectorial.toString();
    }

    public Object agregar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = AgenteSectorialMapper.toEntity(
                new ParserJSON<>(AgenteSectorialJSONDTO.class).parseElement(request.body()));
        this.repositorio.agregar(agenteSectorial);
        return "Agente Sectorial agregado correctamente";
    }

    public Object modificar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = AgenteSectorialMapper.toEntity(
                new ParserJSON<>(AgenteSectorialJSONDTO.class).parseElement(request.body()));
        this.repositorio.modificar(Integer.parseInt(request.params("id")),agenteSectorial);
        return "Agente Sectorial modificado correctamente";
    }

    public Object eliminar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = this.repositorio.buscar(Integer.parseInt(request.params("id")));
        this.repositorio.eliminar(agenteSectorial);
        return "Agente Sectorial eliminado correctamente";
    }
    }
