package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;
import ar.edu.utn.frba.dds.interfaces.gui.dto.AgenteHBS;
import ar.edu.utn.frba.dds.interfaces.gui.dto.OrganizacionHBS;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.AgenteMapperHBS;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.OrganizacionMapperHBS;
import ar.edu.utn.frba.dds.interfaces.input.json.AgenteSectorialJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgenteSectorialController {
    private Repositorio<AgenteSectorial> repoAgentes;
    private Repositorio<AreaSectorial> repoAreas;
    private Repositorio<Organizacion> repoOrganizaciones;

    public AgenteSectorialController() {
        this.repoAgentes = FactoryRepositorio.get(AgenteSectorial.class);
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
    }

    public String mostrarTodos(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        List<AgenteSectorial> agentes = this.repoAgentes.buscarTodos();
        return agentes.toString();
    }

    public AgenteSectorial obtener(Request request, Response response) {
        return this.repoAgentes.buscar(Integer.parseInt(request.params("id")))
                .orElseThrow(EntityNotFoundException::new);
    }

    public Object agregar(Request request, Response response) {
        AgenteSectorialJSONDTO agenteDTO = new ParserJSON<>(AgenteSectorialJSONDTO.class).parseElement(request.body());
        AreaSectorial area = this.repoAreas.buscar(agenteDTO.getArea())
                .orElseThrow(EntityNotFoundException::new);

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

        AgenteSectorial agenteSectorial = this.repoAgentes.buscar(Integer.parseInt(request.params("id")))
                .orElseThrow(EntityNotFoundException::new);
        agenteSectorial.setMail(new ContactoMail(agenteDTO.getContactoMail().direccionesEMail, agenteDTO.getContactoMail().password));
        agenteSectorial.setTelefono(new ContactoTelefono(agenteDTO.getTelefono()));

        this.repoAgentes.modificar(Integer.parseInt(request.params("id")),agenteSectorial);
        return "Agente Sectorial modificado correctamente";
    }

    public Object eliminar(Request request, Response response){
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }    Todo esto agregar una vez que tengamos la vista*/
        AgenteSectorial agenteSectorial = this.repoAgentes.buscar(Integer.parseInt(request.params("id")))
                .orElseThrow(EntityNotFoundException::new);
        this.repoAgentes.eliminar(agenteSectorial);
        return "Agente Sectorial eliminado correctamente";
    }

    public ModelAndView mostrarOrganizaciones(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Integer idAgente = Integer.parseInt(request.params("id"));
        AgenteSectorial agente = this.repoAgentes.buscar(idAgente)
                .orElseThrow(EntityNotFoundException::new);

        parametros.put("rol", "AGENTE"); //todo ver si poner como el menu
        parametros.put("user", agente.getMail().getDireccion()); //todo agregar nombre en agente?
        parametros.put("agenteID", idAgente);

//        List<Organizacion> orgs = repoOrganizaciones.buscarTodos().stream().filter(o -> agente.getArea().getUbicaciones().contains(o.getUbicacion())).collect(Collectors.toList());
        List<Organizacion> orgs = repoOrganizaciones.buscarTodos();
        parametros.put("organizaciones", orgs.stream().map(OrganizacionMapperHBS::toDTOUbicacion).collect(Collectors.toList()));
//        parametros.put("organizaciones", agente.getArea().getOrganizaciones().stream().map(OrganizacionMapperHBS::toDTOUbicacion).collect(Collectors.toList()));
        return new ModelAndView(parametros, "organizaciones.hbs");
    }
}
