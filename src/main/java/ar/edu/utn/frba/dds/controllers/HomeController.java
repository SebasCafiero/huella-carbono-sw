package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.login.LoginController;
import ar.edu.utn.frba.dds.login.UserUtils;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
    private LoginController loginController;
    private RepoMiembros repoMiembros;

    private final Repositorio<AgenteSectorial> repoAgente;

    public HomeController(){
        this.loginController = new LoginController();
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
        this.repoAgente = FactoryRepositorio.get(AgenteSectorial.class);
    }

    public ModelAndView inicio(Request request, Response response) {
        return new ModelAndView(null, "home.hbs");
    }

    public ModelAndView menu(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        return new ModelAndView(null, "menu.hbs");
    }

    public Response reiniciar(Request request, Response response) {

        //todo eliminar todo para que la DB o la Memoria vuelvan al estado inicial
        response.redirect("/home");
        return response;
    }
}
