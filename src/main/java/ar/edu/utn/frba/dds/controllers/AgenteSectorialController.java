package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;
import ar.edu.utn.frba.dds.interfaces.input.json.AgenteSectorialJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import spark.Request;
import spark.Response;

import java.util.List;

public class AgenteSectorialController {
    private Repositorio<AgenteSectorial> repoAgentes;
    private Repositorio<AreaSectorial> repoAreas;
    private LoginController loginController;

    public AgenteSectorialController() {
        this.repoAgentes = FactoryRepositorio.get(AgenteSectorial.class);
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        loginController = new LoginController();
    }

    public String mostrarTodos(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        List<AgenteSectorial> agentes = this.repoAgentes.buscarTodos();
        return agentes.toString();
    }

    public String obtener(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = this.repoAgentes.buscar(Integer.parseInt(request.params("id")));
        return agenteSectorial.toString();
    }

    public Object agregar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorialJSONDTO agenteDTO = new ParserJSON<>(AgenteSectorialJSONDTO.class).parseElement(request.body());
        AreaSectorial area = this.repoAreas.buscar(agenteDTO.getArea());

        AgenteSectorial agenteSectorial = new AgenteSectorial(
                area, new ContactoMail(agenteDTO.getContactoMail().direccionesEMail, agenteDTO.getContactoMail().password),
                new ContactoTelefono(agenteDTO.getTelefono()));

        this.repoAgentes.agregar(agenteSectorial);
        return "Agente Sectorial agregado correctamente";
    }

    public Object modificar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorialJSONDTO agenteDTO = new ParserJSON<>(AgenteSectorialJSONDTO.class).parseElement(request.body());

        AgenteSectorial agenteSectorial = this.repoAgentes.buscar(Integer.parseInt(request.params("id")));
        agenteSectorial.setMail(new ContactoMail(agenteDTO.getContactoMail().direccionesEMail, agenteDTO.getContactoMail().password));
        agenteSectorial.setTelefono(new ContactoTelefono(agenteDTO.getTelefono()));

        this.repoAgentes.modificar(Integer.parseInt(request.params("id")),agenteSectorial);
        return "Agente Sectorial modificado correctamente";
    }

    public Object eliminar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = this.repoAgentes.buscar(Integer.parseInt(request.params("id")));
        this.repoAgentes.eliminar(agenteSectorial);
        return "Agente Sectorial eliminado correctamente";
    }
    }
