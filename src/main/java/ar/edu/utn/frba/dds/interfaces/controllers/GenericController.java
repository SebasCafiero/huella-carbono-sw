package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.server.login.AuthenticationException;
import ar.edu.utn.frba.dds.server.login.User;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class GenericController {
    private final FachadaUsuarios fachadaUsuarios;

    public GenericController() {
        this.fachadaUsuarios = new FachadaUsuarios();
    }

    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.hbs");
    }

    public ModelAndView menu(Request request, Response response) {
        User realUser = new FachadaUsuarios().findById(request.session().attribute("idUsuario"))
                .orElseThrow(AuthenticationException::new);

        HashMap<String, Object> user = new HashMap<>();
        user.put("user", realUser.getUsername());
        user.put("rol", realUser.getRolName());
        user.put("miembro", realUser.getMiembro() != null ? realUser.getMiembro().getId() : null);
        user.put("organizacion", realUser.getOrganizacion() != null ? realUser.getOrganizacion().getId() : null);
        user.put("agente", realUser.getAgenteSectorial() != null ? realUser.getAgenteSectorial().getId() : null);

        return new ModelAndView(user, "menu.hbs");
    }

    public Response iniciarSesion(Request request, Response response) {
        User realUser = fachadaUsuarios.buscarUsuarioEnRepo(request.queryParams("username"), request.queryParams("password"))
                .orElseThrow(AuthenticationException::new);

        request.session().attribute("idUsuario", realUser.getId());
        response.redirect("/menu");
        return response;
    }

    public Object iniciarSesionApi(Request request, Response response) {
        User realUser = fachadaUsuarios.buscarUsuarioEnRepo(request.queryParams("username"), request.queryParams("password"))
                .orElseThrow(AuthenticationException::new);

        HashMap<String, Integer> respuesta = new HashMap<>();
        respuesta.put("idUsuario", realUser.getIdRol());
        request.session().attribute("idUsuario", realUser.getId());
        return respuesta;
    }

    public Response cerrarSesion(Request request, Response response) {
        request.session().removeAttribute("idUsuario");
        response.redirect("/home");
        return response;
    }

    public Response altaUsuario(Request request, Response response) {
        return response;
    }

}
