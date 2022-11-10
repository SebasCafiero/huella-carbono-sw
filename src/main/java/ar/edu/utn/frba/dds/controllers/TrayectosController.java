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
            TramoHBS tramoDTO = new TramoHBS();
            tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporte));
            parametros.put("tramoNuevo", tramoDTO);
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


    public Response agregar(Request req, Response res) {
        Trayecto trayecto = new Trayecto();

        if(req.queryParams("f-trayecto-compartido-ack") != null) {
            int idTrayecto = Integer.parseInt(req.queryParams("f-trayecto-id")); //todo validar
            trayecto = this.fachada.obtenerTrayecto(idTrayecto);
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

    private void asignarCampos(Trayecto trayecto, Request req) { //todo abstraer y emprolijar
        String fechaActual = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
        String[] fecha = req.queryParamOrDefault("f-fecha", fechaActual).split("/"); //todo validar
        trayecto.setPeriodo(new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])));

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

        if(req.queryParams("f-transporte-nuevo") != null) {
            MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("f-transporte-nuevo")));
            Coordenada coorInicial;
            Coordenada coorFinal;
            Tramo tramoNuevo;

            UbicacionGeografica ubicacionInicial;
            UbicacionGeografica ubicacionFinal;
            if(transporte instanceof TransportePublico) {
                int idInicial = Integer.parseInt(req.queryParams("f-transporte-parada-inicial-nueva"));
                int idFinal = Integer.parseInt(req.queryParams("f-transporte-parada-final-nueva"));

                Parada paradaInicial = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idInicial).findFirst().get(); //TRY
                Parada paradaFinal = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idFinal).findFirst().get();

                ubicacionInicial = paradaInicial.getUbicacion();
                ubicacionFinal = paradaFinal.getUbicacion();

            } else {
                coorInicial = new Coordenada(
                        Float.parseFloat(req.queryParams("f-lat-inicial-nueva")),
                        Float.parseFloat(req.queryParams("f-lon-inicial-nueva")));
                coorFinal = new Coordenada(
                        Float.parseFloat(req.queryParams("f-lat-final-nueva")),
                        Float.parseFloat(req.queryParams("f-lon-final-nueva")));

                ubicacionInicial = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("f-provincia-inicial-nueva"),
                        req.queryParams("f-municipio-inicial-nueva"),
                        req.queryParams("f-localidad-inicial-nueva"),
                        req.queryParams("f-calle-inicial-nueva"),
                        Integer.parseInt(req.queryParams("f-numero-inicial-nueva")),
                        coorInicial
                );

                ubicacionFinal = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("f-provincia-final-nueva"),
                        req.queryParams("f-municipio-final-nueva"),
                        req.queryParams("f-localidad-final-nueva"),
                        req.queryParams("f-calle-final-nueva"),
                        Integer.parseInt(req.queryParams("f-numero-final-nueva")),
                        coorFinal
                );
            }
            tramoNuevo = new Tramo(transporte, ubicacionInicial, ubicacionFinal);
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

    public ModelAndView editar(Request req, Response res) { //todo abstraer y emprolijar
        Map<String, Object> parametros = mapUser(req, res);
        int idTrayecto = Integer.parseInt(req.params("trayecto"));
        int idMiembro = Integer.parseInt(req.params("miembro"));
        Miembro miembro = fachada.obtenerMiembro(idMiembro);

        Trayecto trayecto = fachada.obtenerTrayecto(idTrayecto);
        parametros.put("miembroID", idMiembro);
        parametros.put("miembros", trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        parametros.put("transportesTotales", generarMapeoTransportesTipos());

        if(req.queryParams("transporte-nuevo") != null) {
            MedioDeTransporte transporteNuevo = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("transporte-nuevo")));
            TramoHBS tramoDTO = new TramoHBS();
            UbicacionGeografica ubicacionInicial = null;
            UbicacionGeografica ubicacionFinal = null;
            if(transporteNuevo instanceof TransportePublico) {
                if (req.queryParams("parada-inicial-nueva") != null && req.queryParams("parada-final-nueva") != null) {
                    int idParadaInicial = Integer.parseInt(req.queryParams("parada-inicial-nueva"));
                    int idParadaFinal = Integer.parseInt(req.queryParams("parada-final-nueva"));

                    Optional<Parada> op_paradaInicial = ((TransportePublico) transporteNuevo).getParadas().stream().filter(p -> p.getId() == idParadaInicial).findFirst();
                    Optional<Parada> op_paradaFinal = ((TransportePublico) transporteNuevo).getParadas().stream().filter(p -> p.getId() == idParadaFinal).findFirst();

                    if (op_paradaInicial.isPresent() && op_paradaFinal.isPresent()) {
                        Parada paradaInicial = op_paradaInicial.get();
                        Parada paradaFinal = op_paradaFinal.get();

                        ubicacionInicial = paradaInicial.getUbicacion();
                        ubicacionFinal = paradaFinal.getUbicacion();

                        tramoDTO.setIdParadaInicial(idParadaInicial);
                        tramoDTO.setIdParadaFinal(idParadaFinal);
                    }
                }
            } else {
                if(req.queryParams("lat-inicial-nueva") != null
                    && req.queryParams("lat-final-nueva") != null
                    && req.queryParams("prov-inicial-nueva") != null
                    && req.queryParams("prov-final-nueva") != null) { //uso los primeros de coord y de direc, pero quizas validar todos

                    Coordenada coorInicial = new Coordenada(
                        Float.parseFloat(req.queryParams("lat-inicial-nueva")),
                        Float.parseFloat(req.queryParams("lon-inicial-nueva")));
                    Coordenada coorFinal = new Coordenada(
                        Float.parseFloat(req.queryParams("lat-final-nueva")),
                        Float.parseFloat(req.queryParams("lon-final-nueva")));

                    ubicacionInicial = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("prov-inicial-nueva"),
                        req.queryParams("mun-inicial-nueva"),
                        req.queryParams("loc-inicial-nueva"),
                        req.queryParams("calle-inicial-nueva"),
                        Integer.parseInt(req.queryParams("num-inicial-nueva")),
                        coorInicial
                    );

                    ubicacionFinal = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("prov-final-nueva"),
                        req.queryParams("mun-final-nueva"),
                        req.queryParams("loc-final-nueva"),
                        req.queryParams("calle-final-nueva"),
                        Integer.parseInt(req.queryParams("num-final-nueva")),
                        coorFinal
                    );
                }
            }
            if(ubicacionInicial != null)
                tramoDTO.setUbicacionInicial(UbicacionMapperHBS.toDTO(ubicacionInicial));
            else
                tramoDTO.setUbicacionInicial(null);

            if(ubicacionFinal != null)
                tramoDTO.setUbicacionFinal(UbicacionMapperHBS.toDTO(ubicacionFinal));
            else
                tramoDTO.setUbicacionFinal(null);

            tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporteNuevo));

            parametros.put("tramoNuevo", tramoDTO);
        }

        String fechaActual = trayecto.getPeriodo().getMes() + "/" + trayecto.getPeriodo().getAnio();
        String[] fecha = req.queryParamOrDefault("fecha", fechaActual).split("/");

        List<TramoHBS> tramosDTO = new ArrayList<>();
        int cant = 0;
        while(req.queryParams("transporte"+cant) != null) {
            MedioDeTransporte transporte = fachada.obtenerTransporte(Integer.parseInt(req.queryParams("transporte"+cant)));
            TransporteHBS transporteDTO = TransporteMapperHBS.toDTOLazy(transporte); //deberia sacarlo xq no lo uso casi
            TramoHBS tramoDTO = new TramoHBS();
            UbicacionGeografica ubicacionInicial = null;
            UbicacionGeografica ubicacionFinal = null;
            if(transporteDTO.getEsPublico()) {
                if (req.queryParams("parada-inicial" + cant) != null && req.queryParams("parada-final" + cant) != null) {
                    int idParadaInicial = Integer.parseInt(req.queryParams("parada-inicial" + cant));
                    int idParadaFinal = Integer.parseInt(req.queryParams("parada-final" + cant));
                    Optional<Parada> op_paradaInicial = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idParadaInicial).findFirst();
                    Optional<Parada> op_paradaFinal = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idParadaFinal).findFirst();

                    if (op_paradaInicial.isPresent() && op_paradaFinal.isPresent()) {
                        Parada paradaInicial = op_paradaInicial.get();
                        Parada paradaFinal = op_paradaFinal.get();

                        ubicacionInicial = paradaInicial.getUbicacion();
                        ubicacionFinal = paradaFinal.getUbicacion();

                        tramoDTO.setIdParadaInicial(idParadaInicial);
                        tramoDTO.setIdParadaFinal(idParadaFinal);
                    }
                }
            } else {
                if(req.queryParams("lat-inicial"+cant) != null
                && req.queryParams("lat-final"+cant) != null
                && req.queryParams("prov-inicial"+cant) != null
                && req.queryParams("prov-final"+cant) != null) { //uso los primeros de coord y de direc, pero quizas validar todos

                    Coordenada coorInicial = new Coordenada(
                            Float.parseFloat(req.queryParams("lat-inicial"+cant)),
                            Float.parseFloat(req.queryParams("lon-inicial"+cant)));
                    Coordenada coorFinal = new Coordenada(
                            Float.parseFloat(req.queryParams("lat-final"+cant)),
                            Float.parseFloat(req.queryParams("lon-final"+cant)));

                    ubicacionInicial = new UbicacionGeografica(
                            "Argentina",
                            req.queryParams("prov-inicial"+cant),
                            req.queryParams("mun-inicial"+cant),
                            req.queryParams("loc-inicial"+cant),
                            req.queryParams("calle-inicial"+cant),
                            Integer.parseInt(req.queryParams("num-inicial"+cant)),
                            coorInicial
                    );

                    ubicacionFinal = new UbicacionGeografica(
                            "Argentina",
                            req.queryParams("prov-final"+cant),
                            req.queryParams("mun-final"+cant),
                            req.queryParams("loc-final"+cant),
                            req.queryParams("calle-final"+cant),
                            Integer.parseInt(req.queryParams("num-final"+cant)),
                            coorFinal
                    );
                }
            }
            if(ubicacionInicial != null)
                tramoDTO.setUbicacionInicial(UbicacionMapperHBS.toDTO(ubicacionInicial));
            else
                tramoDTO.setUbicacionInicial(null);

            if(ubicacionFinal != null)
                tramoDTO.setUbicacionFinal(UbicacionMapperHBS.toDTO(ubicacionFinal));
            else
                tramoDTO.setUbicacionFinal(null);

            tramoDTO.setId(cant);
            tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporte));
            tramosDTO.add(tramoDTO);
            cant++;
        }
        TrayectoHBS trayectoDTO = new TrayectoHBS();
        trayectoDTO.setMes(Integer.parseInt(fecha[0]));
        trayectoDTO.setAño(Integer.parseInt(fecha[1]));
        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        trayectoDTO.setId(idTrayecto);

        if(tramosDTO.isEmpty()) {
            trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTO).collect(Collectors.toList()));
        } else {
            trayectoDTO.setTramos(tramosDTO);
        }

        parametros.put("trayecto", trayectoDTO);

        return new ModelAndView(parametros, "trayecto-edicion.hbs");
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
