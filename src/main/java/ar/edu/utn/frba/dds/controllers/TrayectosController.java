package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrayectosController {

    private Repositorio<Trayecto> repoTrayectos;
//    private FachadaTrayectos fachada;

    public TrayectosController() {
        this.repoTrayectos = FactoryRepositorio.get(Trayecto.class);
//        this.fachada = new FachadaTrayectos();
    }

    public ModelAndView mostrarTodos(Request req, Response res) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayectos", repoTrayectos.buscarTodos());
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView obtener(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayecto", repoTrayectos.buscar(Integer.parseInt(request.params("id"))));
        if(request.queryParamOrDefault("mode","view").equals("edit"))
            return new ModelAndView(parametros,"trayecto-edicion.hbs");
        else
            return new ModelAndView(parametros, "trayecto.hbs");
    }

    //queryparamsvalues para muchos valores con mismo name

    public String modificar(Request request, Response response) {
        return "modificar";
    }

    public String darAlta(Request request, Response response) {
        return "darAlta";
    }

    public String agregar(Request request, Response response) {
        return "agregar";
    }
}
