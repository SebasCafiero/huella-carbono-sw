package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.server.login.User;
import ar.edu.utn.frba.dds.server.login.UserUtils;
import spark.Request;
import spark.Response;

public class UserController {
    UserUtils userUtils;

    public UserController() { this.userUtils = new UserUtils();}

    public Object agregar(Request request, Response response) throws SectorException {
        User user = new User(request.queryParams("username"), request.queryParams("password"));
        userUtils.agregar(user);
        return "Agregaste correctamente el usuario";
    }
}
