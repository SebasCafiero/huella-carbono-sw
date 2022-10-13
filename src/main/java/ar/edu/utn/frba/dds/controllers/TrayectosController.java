package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.stream.Collectors;

public class TrayectosController {

    private FachadaTrayectos fachada; //TODO volver a poner los repos y sacar fachada?

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
        String modo = request.queryParamOrDefault("mode", "view"); //todo evitar querystring y usar JS?
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
            Map<String, Object> miembroMap = new HashMap<>();
            miembroMap.put("miembro", m);
            if(m.getTrayectos() == null) //por miembros del Data sin inicializar //todo no habria que NO tener instancias irreales, o siempre hay que hacer get y setear todos los valores?
                miembroMap.put("contieneTrayecto", miembrosTrayecto.contains(m));
            else
                miembroMap.put("contieneTrayecto", miembrosTrayecto.contains(m));
            miembros.add(miembroMap);
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
        parametros.put("transportesTotales",fachada.obtenerTransportes());

    }

    public Response modificar(Request request, Response response) {
        Integer id = Integer.parseInt(request.params("id"));
        Trayecto trayecto = fachada.obtenerTrayecto(id);
        asignarCampos(trayecto, request);
        fachada.modificarTrayecto(trayecto);
        response.redirect("/trayecto/"+id);
        return response;
    }

    private void asignarCampos(Trayecto trayecto, Request req) {
        String[] fecha = req.queryParams("f-fecha").split("/"); //TODO VALIDAR
        trayecto.setPeriodo(new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])));

        List<Miembro> miembros = Arrays.stream(req.queryParamsValues("f-miembro"))
                .map(m -> fachada.obtenerMiembro(Integer.parseInt(m)))
                .collect(Collectors.toList());
        trayecto.setMiembros(miembros);

        List<Tramo> tramos = new ArrayList<>();
        int cant = 0; //index de cantidad tramos
        while(req.queryParams("f-transporte-"+cant) != null) { //uso transporte, pero podria ser cualquier campo
            MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("f-transporte-"+cant)));
            Coordenada coorInicial = new Coordenada(
                    Float.parseFloat(req.queryParams("f-lat-inicial-"+cant)),
                    Float.parseFloat(req.queryParams("f-lon-inicial-"+cant)));
            Coordenada coorFinal = new Coordenada(
                    Float.parseFloat(req.queryParams("f-lat-final-"+cant)),
                    Float.parseFloat(req.queryParams("f-lon-final-"+cant)));
            Tramo tramo = new Tramo(transporte, coorInicial, coorFinal);
            tramo.setId(cant);
            tramos.add(tramo);
            cant++;
        }
        trayecto.setTramos(tramos);
    }

    public ModelAndView darAlta(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        //todo ver si agregar org al trayecto para filtrar miembros
        List<Miembro> miembrosTotales = fachada.obtenerMiembros();

        List<Map<String, Object>> miembros = new ArrayList<>();
        miembrosTotales.forEach(m -> {
            Map<String, Object> miembroMap = new HashMap<>();
            miembroMap.put("miembro", m);
            miembroMap.put("contieneTrayecto", false);
            miembros.add(miembroMap);
        });
        parametros.put("miembros", miembros);


        return new ModelAndView(parametros, "/trayecto-edicion.hbs");
    }

    public Response agregar(Request request, Response response) {
        Trayecto trayecto = new Trayecto();
        asignarCampos(trayecto, request);
        this.fachada.cargarTrayecto(trayecto);
        response.redirect("/trayecto/"+trayecto.getId());
        return response;
    }
}
