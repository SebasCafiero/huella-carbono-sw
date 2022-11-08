package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.api.dto.TramoHBS;
import ar.edu.utn.frba.dds.api.dto.TransporteHBS;
import ar.edu.utn.frba.dds.api.dto.TrayectoHBS;
import ar.edu.utn.frba.dds.api.mapper.*;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.*;
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
            return darAlta(request, response);
        return mostrarTodos(request, response); //por default es ?action=list
    }

    public ModelAndView mostrarTodos(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        parametros.put("miembroID", idMiembro);
        parametros.put("trayectos", miembro.getTrayectos().stream().map(TrayectoMapperHBS::toDTOLazy).collect(Collectors.toList()));
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView darAlta(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        parametros.put("miembroID", idMiembro);
        parametros.put("miembroLogueado", MiembroMapperHBS.toDTO(miembro));
        parametros.put("transportesTotales", generarMapeoTransportesTipos());
        parametros.put("fecha", "MES/AÑO");
        if(req.queryParams("transporte-nuevo") != null) {
            MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("transporte-nuevo")));
            parametros.put("transporteNuevo", TransporteMapperHBS.toDTO(transporte));
            parametros.put("fecha", req.queryParams("fecha"));
        }
        return new ModelAndView(parametros, "/trayecto-edicion.hbs");
    }

    private List<Object> generarMapeoTransportesTipos() {
        List<Object> transportes = new ArrayList<>();
        List<MedioDeTransporte> transporteTotales = fachada.obtenerTransportes();
        transportes.add(MediosMapperHBS.toDTOLazy(TransportePublico.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTOLazy(VehiculoParticular.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTOLazy(TransporteEcologico.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTOLazy(ServicioContratado.class, transporteTotales));
        return transportes;
    }

    //1ro tipos de transportes
    //2do si es publico sus paradas / si no es publico nada
    //3ro seleccionar la parada o indicar las coordenadas
    //todo ver que en nuevo trayecto, seleccionar habilita las coordenadas

    private List<Object> generarMapeoTransportesTotales() {
        List<Object> transportes = new ArrayList<>();
        List<MedioDeTransporte> transporteTotales = fachada.obtenerTransportes();
        transportes.add(MediosMapperHBS.toDTO(TransportePublico.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTO(VehiculoParticular.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTO(TransporteEcologico.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTO(ServicioContratado.class, transporteTotales));
        return transportes;
    }

    public Response agregar(Request req, Response res) { //todo CHECK
        Trayecto trayecto = new Trayecto();

        if(req.queryParams("f-trayecto-compartido-ack") != null) {
            int idTrayecto = Integer.parseInt(req.queryParams("f-trayecto-id")); //todo validar
            trayecto = this.fachada.obtenerTrayecto(idTrayecto); //todo el miembro puede duplicar su mismo trayecto
        } else {
            asignarCampos(trayecto, req);
            this.fachada.cargarTrayecto(trayecto);
        }
        int idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        miembro.agregarTrayecto(trayecto);
        trayecto.agregarMiembro(miembro);
        res.redirect("/miembro/" + req.params("id") + "/trayecto/" + trayecto.getId());
        return res;
    }

    private void asignarCampos(Trayecto trayecto, Request req) { //todo CHECK
        String fechaActual = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
        String[] fecha = req.queryParamOrDefault("f-fecha", fechaActual).split("/"); //TODO VALIDAR try si nu son numeros
        trayecto.setPeriodo(new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])));

        //todo VER SI NO TOCO LOS YA EXISTENTES, ESTARAN DESHABILITADOS
        List<Tramo> tramos = new ArrayList<>();
        int cant = 0; //index de cantidad tramos
        while(req.queryParams("f-transporte-"+cant) != null) { //uso transporte, pero podria ser cualquier campo
            Tramo tramo;
            if((req.queryParams("f-transporte-parada-inicial-"+cant) != null) || (req.queryParams("f-lat-inicial-"+cant) != null)) {
                MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("f-transporte-"+cant)));
                UbicacionGeografica ubiInicial;
                UbicacionGeografica ubiFinal;
                if(transporte instanceof TransportePublico) {
                    int idInicial = Integer.parseInt(req.queryParams("f-transporte-parada-inicial-"+cant));
                    int idFinal = Integer.parseInt(req.queryParams("f-transporte-parada-final-"+cant));
                    //todo poner try o validar que las ids sean del transporte correspondiente
                    Parada paradaInicial = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idInicial).findFirst().get(); //TRY
                    Parada paradaFinal = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idFinal).findFirst().get();

                    ubiInicial = paradaInicial.getUbicacion();
                    ubiFinal = paradaFinal.getUbicacion();
                } else {
                    Coordenada coorInicial = new Coordenada(
                            Float.parseFloat(req.queryParams("f-lat-inicial-"+cant)),
                            Float.parseFloat(req.queryParams("f-lon-inicial-"+cant)));
                    Coordenada coorFinal = new Coordenada(
                            Float.parseFloat(req.queryParams("f-lat-final-"+cant)),
                            Float.parseFloat(req.queryParams("f-lon-final-"+cant)));

                    ubiInicial = new UbicacionGeografica(
                            "Argentina",
                            req.queryParams("f-provincia-inicial-"+cant),
                            req.queryParams("f-municipio-inicial-"+cant),
                            req.queryParams("f-localidad-inicial-"+cant),
                            req.queryParams("f-calle-inicial-"+cant),
                            Integer.parseInt(req.queryParams("f-numero-inicial-"+cant)),
                            coorInicial
                    );

                    ubiFinal = new UbicacionGeografica(
                            "Argentina",
                            req.queryParams("f-provincia-final-"+cant),
                            req.queryParams("f-municipio-final-"+cant),
                            req.queryParams("f-localidad-final-"+cant),
                            req.queryParams("f-calle-final-"+cant),
                            Integer.parseInt(req.queryParams("f-numero-final-"+cant)),
                            coorFinal
                    );

                }
                tramo = new Tramo(transporte, ubiInicial, ubiFinal);
            } else { //Cuando se deja sin modificar el tramo
//                coorInicial = trayecto.getTramos().get(cant).getUbicacionInicial().getCoordenada(); //TODO así como ver el seteo del id, ver el orden de la lista (indice es cant?) o cómo buscarlo
//                coorFinal = trayecto.getTramos().get(cant).getUbicacionFinal().getCoordenada();
                tramo = trayecto.getTramos().get(cant);
            }
            tramo.setId(cant); //TODO VER COMO SETEAR EL ID (es propio de cada trayecto?)
            tramos.add(tramo);
            cant++;
        }
        trayecto.setTramos(tramos);

        if(req.queryParams("f-transporte-nuevo") != null) { //todo faltan validar los campos xq no son required (o poner default)
            MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("f-transporte-nuevo")));
            Coordenada coorInicial;
            Coordenada coorFinal;
            Tramo tramoNuevo;

            if(transporte instanceof TransportePublico) {
                int idInicial = Integer.parseInt(req.queryParams("f-transporte-parada-inicial-nueva"));
                int idFinal = Integer.parseInt(req.queryParams("f-transporte-parada-final-nueva"));

                Parada paradaInicial = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idInicial).findFirst().get(); //TRY
                Parada paradaFinal = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idFinal).findFirst().get();

                UbicacionGeografica ubicacionInicial = paradaInicial.getUbicacion(); //todo cambiar que el tramo tenga la parada
                UbicacionGeografica ubicacionFinal = paradaFinal.getUbicacion();

                tramoNuevo = new Tramo(transporte, ubicacionInicial, ubicacionFinal);

            } else {
                coorInicial = new Coordenada(
                        Float.parseFloat(req.queryParams("f-lat-inicial-nueva")),
                        Float.parseFloat(req.queryParams("f-lon-inicial-nueva")));
                coorFinal = new Coordenada(
                        Float.parseFloat(req.queryParams("f-lat-final-nueva")),
                        Float.parseFloat(req.queryParams("f-lon-final-nueva")));
                tramoNuevo = new Tramo(transporte, coorInicial, coorFinal); //todo falta obtener direccion
            }

            tramoNuevo.setId(cant);
            tramos.add(tramoNuevo);
            //cant++;
        }
    }

    public ModelAndView mostrarYEditar(Request req, Response res) {
        String modo = req.queryParamOrDefault("action", "view");
        if(modo.equals("edit"))
            return editar(req, res);
        return obtener(req, res);
    }

    public ModelAndView obtener(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idTrayecto = Integer.parseInt(req.params("trayecto"));
        int idMiembro = Integer.parseInt(req.params("miembro"));
        try { //todo no se valida que el trayecto sea del miembro (xq falta validar el user)
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            parametros.put("trayecto", TrayectoMapperHBS.toDTO(trayecto));
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

    public ModelAndView editar(Request req, Response res) {
        Map<String, Object> parametros = mapUser(req, res);
        int idTrayecto = Integer.parseInt(req.params("trayecto"));
        int idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
//        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            parametros.put("trayecto", TrayectoMapperHBS.toDTO(trayecto));
            parametros.put("miembroID", idMiembro);
            parametros.put("miembros", trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
            parametros.put("transportesTotales", generarMapeoTransportesTipos());

            if(req.queryParams("transporte-nuevo") != null) {
                MedioDeTransporte transporteNuevo = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("transporte-nuevo")));
                parametros.put("transporteNuevo", TransporteMapperHBS.toDTO(transporteNuevo));
//                parametros.put("fecha", req.queryParams("fecha"));
            }

            Trayecto trayectoBuff = new Trayecto(); //todo check que no se cambie el original
            trayectoBuff.setTramos(trayecto.getTramos());
            trayectoBuff.setPeriodo(trayecto.getPeriodo());
            List<Tramo> tramos = new ArrayList<>();
            List<TramoHBS> tramosDTO = new ArrayList<>();
            int cant = 0;
            int publicos = 0;
            int no_publicos = 0;
            while(req.queryParams("transporte"+cant) != null) {
                MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("transporte"+cant)));
                Tramo tramo;
                TramoHBS tramoDTO;
                if(transporte instanceof TransportePublico) {
                    try {
                        int idInicial = Integer.parseInt(req.queryParams("parada-inicial"+publicos)); //todo falta en js...
                        int idFinal = Integer.parseInt(req.queryParams("parada-final"+publicos));

                        Optional<Parada> op_paradaInicial = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idInicial).findFirst(); //TRY
                        Optional<Parada> op_paradaFinal = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idFinal).findFirst();

                        Parada paradaInicial = op_paradaInicial.get();
                        Parada paradaFinal = op_paradaFinal.get();

                        UbicacionGeografica ubicacionInicial = paradaInicial.getUbicacion();
                        UbicacionGeografica ubicacionFinal = paradaFinal.getUbicacion();
                        tramo = new Tramo(transporte, ubicacionInicial, ubicacionFinal);
                        tramoDTO = TramoMapperHBS.setDTOTransporte(TramoMapperHBS.toDTO(tramo), transporte);
                        tramo.setId(cant); //todo check
                        tramos.add(tramo);

                    } catch (NoSuchElementException | NumberFormatException e) {
                        tramoDTO = TramoMapperHBS.setDTOTransporte(new TramoHBS(), transporte);

                    }

                    publicos++;
                } else {

                    Coordenada coorInicial = new Coordenada(
                            Float.parseFloat(req.queryParams("lat-inicial"+no_publicos)),
                            Float.parseFloat(req.queryParams("lon-inicial"+no_publicos)));
                    Coordenada coorFinal = new Coordenada(
                            Float.parseFloat(req.queryParams("lat-final"+no_publicos)),
                            Float.parseFloat(req.queryParams("lon-final"+no_publicos)));

                    UbicacionGeografica ubiInicial = new UbicacionGeografica(
                            "Argentina",
                            req.queryParams("prov-inicial"+no_publicos),
                            req.queryParams("mun-inicial"+no_publicos),
                            req.queryParams("loc-inicial"+no_publicos),
                            req.queryParams("calle-inicial"+no_publicos),
                            Integer.parseInt(req.queryParams("num-inicial"+no_publicos)),
                            coorInicial
                            );

                    UbicacionGeografica ubiFinal = new UbicacionGeografica(
                            "Argentina",
                            req.queryParams("prov-final"+no_publicos),
                            req.queryParams("mun-final"+no_publicos),
                            req.queryParams("loc-final"+no_publicos),
                            req.queryParams("calle-final"+no_publicos),
                            Integer.parseInt(req.queryParams("num-final"+no_publicos)),
                            coorFinal
                    );

                    tramo = new Tramo(transporte, ubiInicial, ubiFinal);
                    tramoDTO = TramoMapperHBS.toDTO(tramo);
                    no_publicos++;
                }
                tramoDTO.setId(cant);
                tramosDTO.add(tramoDTO);
                cant++;
            }
            TrayectoHBS trayectoDTO;
            if(tramosDTO.isEmpty()) {
                trayectoBuff.setTramos(trayecto.getTramos());
                trayectoDTO = TrayectoMapperHBS.toDTOEditable(trayectoBuff);
            } else {
                trayectoBuff.setTramos(tramos);
                trayectoDTO = TrayectoMapperHBS.toDTOEditable(trayectoBuff);
                trayectoDTO.setTramos(tramosDTO);
            }
            parametros.put("trayectoBuff", trayectoDTO);

            return new ModelAndView(parametros, "trayecto-edicion.hbs");
//        } catch (NullPointerException e) {
//            res.status(400);
//            String errorDesc = "Trayecto de id "+idTrayecto+" inexistente";
//            parametros.put("descripcion", errorDesc);
//            parametros.put("codigo", res.status());
//            return new ErrorResponse(errorDesc).generarVista(parametros);
//        }
    }

    public Response modificar(Request req, Response res) { //todo CHECK
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);
        try {
            Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
            asignarCampos(trayecto, req);
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

    public Response eliminar(Request req, Response res) { //todo ver CONFIRMACION
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
            trayecto.getMiembros().forEach(m -> m.quitarTrayecto(trayecto));
            trayecto.setMiembros(new ArrayList<>()); //todo no puedo usar foreach de miembros por problemas de concurrencia
            this.fachada.eliminarTrayecto(trayecto);
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
