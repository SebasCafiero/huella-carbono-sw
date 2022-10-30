package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.login.LoginController;
import ar.edu.utn.frba.dds.login.UserUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
    private LoginController loginController = new LoginController();

    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.html");
    }

    public ModelAndView menu(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }
        return new ModelAndView(null, "menu.html");
    }

    public Response reiniciar(Request request, Response response) {

        //todo eliminar todo para que la DB o la Memoria vuelvan al estado inicial
        response.redirect("/home");
        return response;
    }
}
