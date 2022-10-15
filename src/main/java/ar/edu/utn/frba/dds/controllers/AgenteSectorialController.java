package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.mapping.AgenteSectorialMapper;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserAgentesJSON;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.Request;
import spark.Response;

import java.util.List;

public class AgenteSectorialController {
    private Repositorio<AgenteSectorial> repositorio;

    public AgenteSectorialController() {this.repositorio = FactoryRepositorio.get(AgenteSectorial.class);}

    public String mostrarTodos(Request request, Response response) {
        List<AgenteSectorial> agentes =this.repositorio.buscarTodos();
        return agentes.toString();
    }

    public String obtener(Request request, Response response){
        AgenteSectorial agenteSectorial = this.repositorio.buscar(Integer.parseInt(request.params("id")));
        return agenteSectorial.toString();
    }

    public Object agregar(Request request, Response response){
        AgenteSectorial agenteSectorial = AgenteSectorialMapper.toEntity(ParserAgentesJSON.generarAgente(request.body()));
        this.repositorio.agregar(agenteSectorial);
        return "Agente Sectorial agregado correctamente";
    }

    public Object modificar(Request request, Response response){
        AgenteSectorial agenteSectorial = AgenteSectorialMapper.toEntity(ParserAgentesJSON.generarAgente(request.body()));
        this.repositorio.modificar(Integer.parseInt(request.params("id")),agenteSectorial);
        return "Agente Sectorial modificado correctamente";
    }

    public Object eliminar(Request request, Response response){
        AgenteSectorial agenteSectorial = this.repositorio.buscar(Integer.parseInt(request.params("id")));
        this.repositorio.eliminar(agenteSectorial);
        return "Agente Sectorial eliminado correctamente";
    }
    }
