package ar.edu.utn.frba.dds.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.hbs");
    }

    public ModelAndView menu(Request request, Response response) {
        return new ModelAndView(null, "menu.hbs");
    }

    public Response reiniciar(Request request, Response response) {

        //todo eliminar todo para que la DB o la Memoria vuelvan al estado inicial
        response.redirect("/home");
        return response;
    }
}
