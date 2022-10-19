package ar.edu.utn.frba.dds.login;


import ar.edu.utn.frba.dds.entities.lugares.SectorException;

import spark.*;

import java.util.*;

public class LoginController {

    UserUtils userUtils;

    public LoginController() { this.userUtils = new UserUtils();}

    public Void intentarLogear(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        if (!userUtils.authenticate(request.queryParams("username"), request.queryParams("password"))) {
            model.put("authenticationFailed", true);
            response.redirect("/home");
            return null;
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", request.queryParams("username"));
        response.redirect("/menu");
        return null;
    };
}
