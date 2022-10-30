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
import ar.edu.utn.frba.dds.login.LoginController;
import ar.edu.utn.frba.dds.mihuella.dto.ErrorResponse;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TrayectosController {

    private FachadaTrayectos fachada;
    private LoginController loginController;

    public TrayectosController() {
        this.fachada = new FachadaTrayectos();
        this.loginController = new LoginController();
    }

    public ModelAndView mostrarTodos(Request req, Response res) {
        if (loginController.chequearValidezAcceso(req, res, true) != null){
            return loginController.chequearValidezAcceso(req, res, true);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayectos", fachada.obtenerTrayectos());
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView obtener(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        Map<String, Object> parametros = new HashMap<>();
        int idTrayecto = Integer.parseInt(request.params("id"));

        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            parametros.put("trayecto", trayecto);
            String modo = request.queryParamOrDefault("mode", "view"); //todo evitar querystring y usar JS?
            if(modo.equals("edit")) {
                setearCampos(trayecto, parametros);
                return new ModelAndView(parametros,"trayecto-edicion.hbs");
            } else {
                return new ModelAndView(parametros, "trayecto.hbs");
            }
        } catch (NullPointerException e) {
            response.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            parametros.put("descripcion", errorDesc);
            parametros.put("codigo", response.status());
            return new ErrorResponse(errorDesc).generarVista(parametros);
        }
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

        try {
            Trayecto trayecto = fachada.obtenerTrayecto(id);
            if(request.queryParams("f-eliminar") != null)
                if(request.queryParams("f-eliminar").equals("true")) //innecesario quizas
                    return this.eliminar(request, response); //todo lo deje para mostrar a Eze pero ya está el boton con JS para DELETE
            asignarCampos(trayecto, request);
            fachada.modificarTrayecto(trayecto);
        } catch (NullPointerException e) {
            response.status(400);
            String errorDesc = "Trayecto de id "+id+" inexistente";
            //parametros.put("descripcion", errorDesc);
            //parametros.put("codigo", response.status());
            //return new ErrorResponse(errorDesc).generarVista(parametros);
            response.redirect("/error/"+400);
            return response; //todo check, no puedo retornar vista
        }

        response.redirect("/trayecto/"+id);
        return response;
    }

    private void asignarCampos(Trayecto trayecto, Request req) {
        String fechaActual = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
        String[] fecha = req.queryParamOrDefault("f-fecha", fechaActual).split("/"); //TODO VALIDAR
        trayecto.setPeriodo(new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])));

        if(req.queryParamsValues("f-miembro") != null) {
            List<Miembro> miembros = Arrays.stream(req.queryParamsValues("f-miembro")) //todo validar
                    .map(m -> fachada.obtenerMiembro(Integer.parseInt(m)))
                    .collect(Collectors.toList());
            trayecto.setMiembros(miembros);
        } else
            trayecto.setMiembros(new ArrayList<>());

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

        if(req.queryParams("f-transporte-nuevo") != null) { //todo faltan validar los campos xq no son required (o poner default)
            MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("f-transporte-nuevo")));
            Coordenada coorInicial = new Coordenada(
                    Float.parseFloat(req.queryParams("f-lat-inicial-nueva")),
                    Float.parseFloat(req.queryParams("f-lon-inicial-nueva")));
            Coordenada coorFinal = new Coordenada(
                    Float.parseFloat(req.queryParams("f-lat-final-nueva")),
                    Float.parseFloat(req.queryParams("f-lon-final-nueva")));
            Tramo tramoNuevo = new Tramo(transporte, coorInicial, coorFinal);
            tramoNuevo.setId(cant);
            tramos.add(tramoNuevo);
            //cant++;
        }
    }

    public Response eliminar(Request request, Response response) {
        int id = Integer.parseInt(request.params("id"));
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(id);
            fachada.eliminarTrayecto(trayecto);
        } catch (NullPointerException e) {
            String errorDesc = "Trayecto de id "+id+" inexistente.";
            response.status(400);
            response.redirect("/error/"+400);
            //return new ErrorResponse(errorDesc).generarVista(parametros); //todo no puedo retornar vista
        }
//        response.redirect("/trayectos"); //redirijo desde JS
        return response;
    }

    public ModelAndView darAlta(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

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
        parametros.put("transportesTotales",fachada.obtenerTransportes());
        //VER DE REUTILIZAR setearCampos()
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
