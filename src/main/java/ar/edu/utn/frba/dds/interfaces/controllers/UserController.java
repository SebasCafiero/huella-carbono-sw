package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.servicios.fachadas.FachadaUsuarios;

public class UserController {
    FachadaUsuarios fachadaUsuarios;

    public UserController() { this.fachadaUsuarios = new FachadaUsuarios();}

//    public Object agregar(Request request, Response response) throws SectorException {
//        User user = new User(request.queryParams("username"), request.queryParams("password"));
//        fachadaUsuarios.agregar(user);
//        return "Agregaste correctamente el usuario";
//    }
}
