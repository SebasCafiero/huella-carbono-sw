package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
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

    public TrayectosController() {
        this.fachada = new FachadaTrayectos();
    }


    private Map<String, Object> mapUser(Request request, Response response) {
        String username = request.session().attribute("currentUser");
//        User user = new UserUtils().buscar(username);
        Map<String, Object> parametros = new HashMap<>();

        int id;
//        id = user.getRolId(); // id del miembro
        id = 2;

        String rol;
//        rol = user.getRol(); //miembro
        rol = "miembro";

        String name;
//        name = user.getName();
        name = "LEO MESSI";

        parametros.put("rol", rol.toUpperCase(Locale.ROOT));
        parametros.put(rol, id);
        parametros.put("user", name);
        return parametros;
    }


    public ModelAndView mostrarTodosYCrear(Request request, Response response) {
        String modo = request.queryParamOrDefault("action", "list");
        if(modo.equals("create"))
            return darAlta2(request, response);
//            return darAlta(request, response);
        return mostrarTodos(request, response); //por default es ?action=list
    }

    public ModelAndView mostrarTodos(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        parametros.put("trayectos", miembro.getTrayectos()); //TODO ver de abstraer
        parametros.put("miembroID", idMiembro);
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView darAlta(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        parametros.put("miembroID", idMiembro);

        parametros.put("miembroLogueado", mapeoMiembro(miembro));

        List<Miembro> miembrosTotales = fachada.obtenerMiembros().stream().filter(m -> !m.equals(miembro)).collect(Collectors.toList());

        List<Map<String, Object>> miembros = new ArrayList<>();
        miembrosTotales.forEach(m -> miembros.add(mapeoMiembro(m)));
        parametros.put("miembros", miembros);

        parametros.put("transportesTotales", fachada.obtenerTransportes()); //todo separar transportes, y en publicos las paradas
//        List<Map<String, Object>> miembros = new ArrayList<>();
//        miembrosTotales.forEach(m -> {
//            Map<String, Object> miembroMap = new HashMap<>();
//            miembroMap.put("miembro", m);
//            miembroMap.put("contieneTrayecto", false);
//            miembros.add(miembroMap);
//        });
//        parametros.put("miembros", miembros);
//        parametros.put("transportesTotales",fachada.obtenerTransportes());

        //TODO VER DE REUTILIZAR setearCampos()
        return new ModelAndView(parametros, "/trayecto-edicion.hbs");
    }

    private Map<String, Object> mapeoMiembro(Miembro miembro) {
        Map<String, Object> miembroMap = new HashMap<>();
        miembroMap.put("nombre", miembro.getNombre());
        miembroMap.put("apellido", miembro.getApellido());
        miembroMap.put("tipoDeDocumento", miembro.getTipoDeDocumento());
        miembroMap.put("nroDocumento", miembro.getNroDocumento());
        miembroMap.put("id", miembro.getId());

        miembroMap.put("contieneTrayecto", false); //todo cuando es editar trayecto
        return miembroMap;
    }

    public Response agregar(Request req, Response res) {
        Trayecto trayecto = new Trayecto();
        asignarCampos(trayecto, req);
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        miembro.agregarTrayecto(trayecto);
        this.fachada.cargarTrayecto(trayecto);
        res.redirect("/miembro/" + req.params("id") + "/trayecto/" + trayecto.getId());
        return res;
    }

    private void asignarCampos(Trayecto trayecto, Request req) {
        String fechaActual = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
        String[] fecha = req.queryParamOrDefault("f-fecha", fechaActual).split("/"); //TODO VALIDAR
        trayecto.setPeriodo(new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])));

        Miembro miembroLogueado = this.fachada.obtenerMiembro(Integer.parseInt(req.queryParams("f-logueado-data"))); //Puede omitirse si atrapo el User con la session y dsp el miembro asociado
//        List<Miembro> miembros = Collections.singletonList(miembroLogueado);
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(miembroLogueado);

        if(req.queryParamsValues("f-miembro") != null) {
            miembros.addAll(
                    Arrays.stream(req.queryParamsValues("f-miembro")) //todo ver tema de trayecto compartido
                    .map(m -> fachada.obtenerMiembro(Integer.parseInt(m)))
                    .collect(Collectors.toList())
            );
        }
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

    public ModelAndView mostrarYEditar(Request req, Response res) {
        String modo = req.queryParamOrDefault("action", "view");
        if(modo.equals("edit")) {
            return editar2(req, res);
//            return editar(req, res);
        }
        return obtener(req, res);
    }

    public ModelAndView obtener(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idTrayecto = Integer.parseInt(req.params("trayecto"));
        int idMiembro = Integer.parseInt(req.params("miembro"));
        try { //todo no se valida que el trayecto sea del miembro (xq falta validar el user)
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            parametros.put("trayecto", mapeoTrayecto(trayecto));
            parametros.put("miembroID", idMiembro);
            return new ModelAndView(parametros, "trayecto.hbs");
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            parametros.put("descripcion", errorDesc);
            parametros.put("codigo", res.status());
            return new ErrorResponse(errorDesc).generarVista(parametros);
        }
    }

    private Map<String, Object> mapeoTrayecto(Trayecto trayecto) {
        Map<String, Object> trayectoMap = new HashMap<>();
        trayectoMap.put("id", trayecto.getId());
        trayectoMap.put("mes", trayecto.getPeriodo().getMes());
        trayectoMap.put("año", trayecto.getPeriodo().getAnio());
        trayectoMap.put("tramos", trayecto.getTramos()); //todo

        List<Map<String, Object>> miembros = new ArrayList<>();
        trayecto.getMiembros().forEach(m -> miembros.add(mapeoMiembro(m)));
//        trayectoMap.put("miembros", trayecto.getMiembros().stream().map(this::mapeoMiembro).collect(Collectors.toList()));
        trayectoMap.put("miembros", miembros);
        return trayectoMap;
    }

    public ModelAndView editar(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idTrayecto = Integer.parseInt(req.params("trayecto"));
        int idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            parametros.put("trayecto", mapeoTrayecto(trayecto));
            parametros.put("miembroID", idMiembro);
            parametros.put("miembroLogueado", mapeoMiembro(miembro));

//            List<Miembro> miembrosTotales = trayecto.getMiembros(); //todo ver miembros si filtrar, si compartido, y en darAlta
            List<Miembro> miembrosTotales = fachada.obtenerMiembros().stream().filter(m -> !m.equals(miembro)).collect(Collectors.toList());

            List<Map<String, Object>> miembros = new ArrayList<>();
            miembrosTotales.forEach(m -> miembros.add(mapeoMiembro(m)));
            parametros.put("miembros", miembros);
            parametros.put("transportesTotales", fachada.obtenerTransportes()); //todo separar transportes, y en publicos las paradas
            return new ModelAndView(parametros, "trayecto-edicion.hbs");
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            parametros.put("descripcion", errorDesc);
            parametros.put("codigo", res.status());
            return new ErrorResponse(errorDesc).generarVista(parametros);
        }
    }

    public Response modificar(Request req, Response res) {
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            asignarCampos(trayecto, req);
            fachada.modificarTrayecto(trayecto); //todo check que el miembro lo siga teniendo, sino miembro.agregarTrayecto(trayecto)
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            //parametros.put("descripcion", errorDesc);
            //parametros.put("codigo", response.status());
            //return new ErrorResponse(errorDesc).generarVista(parametros);
            res.redirect("/error/"+400);
            return res; //todo check, no puedo retornar vista
        }

        res.redirect("/miembro/" + idMiembro + "/trayecto/" + idTrayecto);
        return res;
    }

    public Response eliminar(Request req, Response res) { //todo ver CONFIRMACION
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            miembro.quitarTrayecto(trayecto);
            fachada.eliminarTrayecto(trayecto);
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            //parametros.put("descripcion", errorDesc);
            //parametros.put("codigo", response.status());
            //return new ErrorResponse(errorDesc).generarVista(parametros);
            res.redirect("/error/"+400);
            return res; //todo check, no puedo retornar vista
        }

//        res.redirect("/miembro/" + idMiembro + "/trayecto?action=list"); //redirijo desde JS
        return res;
    }



    /*private void setearCampos(Trayecto trayecto, Map<String, Object> parametros) {
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

    }*/


    public ModelAndView darAlta2(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        parametros.put("miembroID", idMiembro);

        parametros.put("miembroLogueado", mapeoMiembro(miembro));

        parametros.put("transportesTotales", fachada.obtenerTransportes()); //todo separar transportes, y en publicos las paradas
//        List<Map<String, Object>> miembros = new ArrayList<>();
//        miembrosTotales.forEach(m -> {
//            Map<String, Object> miembroMap = new HashMap<>();
//            miembroMap.put("miembro", m);
//            miembroMap.put("contieneTrayecto", false);
//            miembros.add(miembroMap);
//        });
//        parametros.put("miembros", miembros);
//        parametros.put("transportesTotales",fachada.obtenerTransportes());

        //TODO VER DE REUTILIZAR setearCampos()
        return new ModelAndView(parametros, "/trayecto-edicion2.hbs");
    }

    public Response agregar2(Request req, Response res) {
        Trayecto trayecto = new Trayecto();

        if(req.queryParams("f-trayecto-compartido-ack") != null) {
            int idTrayecto = Integer.parseInt(req.queryParams("f-trayecto-id")); //todo validar
            trayecto = this.fachada.obtenerTrayecto(idTrayecto); //todo el miembro puede duplicar su mismo trayecto
        } else {
            asignarCampos2(trayecto, req);
            this.fachada.cargarTrayecto(trayecto);
        }
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        miembro.agregarTrayecto(trayecto);
        trayecto.agregarMiembro(miembro);
        res.redirect("/miembro/" + req.params("id") + "/trayecto/" + trayecto.getId());
        return res;
    }

    private void asignarCampos2(Trayecto trayecto, Request req) {
        String fechaActual = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
        String[] fecha = req.queryParamOrDefault("f-fecha", fechaActual).split("/"); //TODO VALIDAR
        trayecto.setPeriodo(new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])));

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

    public ModelAndView editar2(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idTrayecto = Integer.parseInt(req.params("trayecto"));
        int idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            parametros.put("trayecto", mapeoTrayecto(trayecto));
            parametros.put("miembroID", idMiembro);
//            parametros.put("miembroLogueado", mapeoMiembro(miembro));

            List<Miembro> miembrosTrayecto = trayecto.getMiembros();

            List<Map<String, Object>> miembros = new ArrayList<>();
            miembrosTrayecto.forEach(m -> miembros.add(mapeoMiembro(m)));
            parametros.put("miembros", miembros);

            parametros.put("transportesTotales", fachada.obtenerTransportes()); //todo separar transportes, y en publicos las paradas
            return new ModelAndView(parametros, "trayecto-edicion2.hbs");
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            parametros.put("descripcion", errorDesc);
            parametros.put("codigo", res.status());
            return new ErrorResponse(errorDesc).generarVista(parametros);
        }
    }

    public Response modificar2(Request req, Response res) {
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            asignarCampos2(trayecto, req);
            fachada.modificarTrayecto(trayecto);
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            //parametros.put("descripcion", errorDesc);
            //parametros.put("codigo", response.status());
            //return new ErrorResponse(errorDesc).generarVista(parametros);
            res.redirect("/error/"+400);
            return res; //todo check, no puedo retornar vista
        }

        res.redirect("/miembro/" + idMiembro + "/trayecto/" + idTrayecto);
        return res;
    }

    public Response eliminar2(Request req, Response res) { //todo ver CONFIRMACION
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            miembro.quitarTrayecto(trayecto);
            trayecto.quitarMiembro(miembro);
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            //parametros.put("descripcion", errorDesc);
            //parametros.put("codigo", response.status());
            //return new ErrorResponse(errorDesc).generarVista(parametros);
            res.redirect("/error/"+400);
//            return res; //todo check, no puedo retornar vista
        }

//        res.redirect("/miembro/" + idMiembro + "/trayecto?action=list"); //redirijo desde JS
        return res;
    }

    public Response borrar(Request req, Response res) {
        Integer idTrayecto = Integer.parseInt(req.params("id"));
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            this.fachada.eliminarTrayecto(trayecto);
            //todo y sacar de cada miembro
        } catch (NullPointerException e) {
            res.status(400);
            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
            //parametros.put("descripcion", errorDesc);
            //parametros.put("codigo", response.status());
            //return new ErrorResponse(errorDesc).generarVista(parametros);
            res.redirect("/error/"+400);
//            return res; //todo check, no puedo retornar vista
        }
        return res;
    }


}
