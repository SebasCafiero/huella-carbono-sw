package ar.edu.utn.frba.dds.login;


import ar.edu.utn.frba.dds.entities.lugares.SectorException;

import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoUsuarios;
import spark.*;

import java.util.*;

public class LoginController {

    UserUtils userUtils;

    public LoginController() {
        this.userUtils = new UserUtils();
    }

    public Void intentarLogear(Request request, Response response) {
        if (userUtils.estaLogueado(request)) {
            response.redirect("/errorAcceso");
        }
        Map<String, Object> model = new HashMap<>();
        if (!userUtils.authenticate(request.queryParams("username"), request.queryParams("password"))) {
            model.put("authenticationFailed", true);
            response.redirect("/home");
            return null;
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("idUsuario", (userUtils.buscarUsuarioEnRepo(request.queryParams("username"), request.queryParams("password"))).get().getId());
        response.redirect("/menu");
        return null;
    }

    public Object agregar(Request request, Response response) throws SectorException {
        User user = new User(request.queryParams("username"), request.queryParams("password"));
        userUtils.agregar(user);
        return "Agregaste correctamente el usuario";
    }

    public Void cerrarSesion(Request request, Response response) {
        if (!userUtils.estaLogueado(request)) {
            response.redirect("/errorAcceso");
            return null;
        }
        request.session().removeAttribute("idUsuario");
        return null;
    }

    public ModelAndView errorAcceso(Request request, Response response){
        return new ModelAndView(null, "errorAcceso.html");
    }

    public ModelAndView chequearValidezAcceso(Request request, Response response, Boolean valorAceptado) {
        if (valorAceptado != userUtils.estaLogueado(request)) {
            response.status(403);
            return new ModelAndView(null, "/errorAcceso.html");
        }
        return null;
    }
}
