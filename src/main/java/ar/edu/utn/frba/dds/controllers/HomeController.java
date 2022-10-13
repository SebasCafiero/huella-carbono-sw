package ar.edu.utn.frba.dds.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.html");
    }
}
