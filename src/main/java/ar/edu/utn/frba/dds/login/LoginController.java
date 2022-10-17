package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.SectorException;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserOrganizacionesJSON;
import spark.*;

import java.util.*;

public class LoginController {

    UserUtils userUtils;

    public LoginController() { this.userUtils = new UserUtils();}

    public int intentarLogear(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        if (!userUtils.authenticate(request.queryParams("username"), request.queryParams("password"))) {
            model.put("authenticationFailed", true);
            return 401;
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", request.queryParams("username"));
        return 200;
    };

    public Object agregar(Request request, Response response) throws SectorException {
        User user = new User(request.queryParams("username"), request.queryParams("password"));
        userUtils.agregar(user);
        return "Agregaste correctamente el usuario";
    }
}
