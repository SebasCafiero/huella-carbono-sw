package ar.edu.utn.frba.dds.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class HomeController {
    private LoginController loginController = new LoginController();

    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.hbs");
    }

    public ModelAndView menu(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }
        HashMap<String, String> data = new HashMap<>();
//        data.put("rol", re)
        return new ModelAndView(null, "menu.hbs");
    }

}
