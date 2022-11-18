package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.servicios.fachadas.FachadaUsuarios;
import spark.*;

import java.util.*;

public class LoginController {

    FachadaUsuarios fachadaUsuarios;

    public LoginController() {
        this.fachadaUsuarios = new FachadaUsuarios();
    }

    public Void intentarLogear(Request request, Response response) {
        if (fachadaUsuarios.estaLogueado(request)) {
            response.redirect("/errorAcceso");
        }
        Map<String, Object> model = new HashMap<>();
        if (!fachadaUsuarios.isAuthenticated(request.queryParams("username"), request.queryParams("password"))) {
            model.put("authenticationFailed", true);
            response.redirect("/home");
            return null;
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("idUsuario", fachadaUsuarios.buscarUsuarioEnRepo(request.queryParams("username"), request.queryParams("password")).get().getId());
        response.redirect("/menu");
        return null;
    }

//    public Object agregar(Request request, Response response) throws SectorException {
//        User user = new User(request.queryParams("username"), request.queryParams("password"));
//        fachadaUsuarios.agregar(user);
//        return "Agregaste correctamente el usuario";
//    }

    public Response cerrarSesion(Request request, Response response) {
        if (!fachadaUsuarios.estaLogueado(request))
            response.redirect("/errorAcceso");
        else {
            request.session().removeAttribute("idUsuario");
            response.redirect("/home");
        }
        return response;
    }

    public ModelAndView errorAcceso(Request request, Response response){
        return new ModelAndView(null, "errorAcceso.html");
    }

    public ModelAndView chequearValidezAcceso(Request request, Response response, Boolean valorAceptado) {
        if (valorAceptado != fachadaUsuarios.estaLogueado(request)) {
            response.status(403);
            return new ModelAndView(null, "/errorAcceso.html");
        }
        return null;
    }
}
