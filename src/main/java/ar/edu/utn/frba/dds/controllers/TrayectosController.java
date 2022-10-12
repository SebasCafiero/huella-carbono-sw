package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrayectosController {

    private FachadaTrayectos fachada;

    public TrayectosController() {
        this.fachada = new FachadaTrayectos();
    }

    public ModelAndView mostrarTodos(Request req, Response res) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayectos", fachada.obtenerTrayectos());
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView obtener(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Trayecto trayecto = fachada.obtenerTrayecto(Integer.parseInt(request.params("id")));
        parametros.put("trayecto", trayecto);
        String modo = request.queryParamOrDefault("mode", "view");
        if(modo.equals("edit")) {
            setearCampos(trayecto, parametros);
            return new ModelAndView(parametros,"trayecto-edicion.hbs");
        }
        else if(modo.equals("view")){
            return new ModelAndView(parametros, "trayecto.hbs");
        } else
            return new ModelAndView(null,"ERROR"); //todo no llega por default

    }

    private void setearCampos(Trayecto trayecto, Map<String, Object> parametros) {
        List<Miembro> miembrosTrayecto = trayecto.getMiembros();

        //todo ver si agregar org al trayecto para filtrar miembros
        List<Miembro> miembrosTotales = fachada.obtenerMiembros();

        List<Map<String, Object>> miembros = new ArrayList<>();
        miembrosTotales.forEach(m -> {
            Map<String, Object> miembroBoolean = new HashMap<>();
            miembroBoolean.put("miembro", m);
            if(m.getTrayectos() == null) //por miembros del Data sin inicializar //todo no habria que NO tener instancias irreales, o siempre hay que hacer get y setear todos los valores?
                miembroBoolean.put("contieneTrayecto", false);
            else
                miembroBoolean.put("contieneTrayecto", miembrosTrayecto.contains(m));
            miembros.add(miembroBoolean);
        });
        parametros.put("miembros", miembros);

//        List<Map<String, Object>> tramos = new ArrayList<>();
//        List<Tramo> tramosTrayecto = trayecto.getTramos();
//
//        tramosTrayecto.forEach(t -> {
//            Map<String, Object> tramoBoolean = new HashMap<>();
//            tramoBoolean.put("tramo", t);
//            tramoBoolean.put("")
//        });
//        List<MedioDeTransporte> transportesTotales = fachada.obtenerTransportes();
//        transportesTotales.forEach(mt -> {
//            Map<String, Object> transporteBoolean = new HashMap<>();
//            transporteBoolean.put("transporte", mt);
//            if()
//        });
//
//        parametros.put("tramos", tramos);
        parametros.put("transportes",fachada.obtenerTransportes());

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
