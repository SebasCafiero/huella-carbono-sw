package ar.edu.utn.frba.dds.login;

import spark.*;

import java.util.*;

public class LoginController {

    public int intentarLogear(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        if (!UserUtils.authenticate(request.queryParams("username"), request.queryParams("password"))) {
            model.put("authenticationFailed", true);
            return 401;
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", request.queryParams("username"));
        return 200;
    };
}
