package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.exceptions.TrayectoConMiembroRepetidoException;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TramoHBS;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TransporteHBS;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TrayectoHBS;
import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.*;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaTrayectos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TrayectosController {
    private final FachadaTrayectos fachadaTrayectos;

    public TrayectosController() {
        this.fachadaTrayectos = new FachadaTrayectos();
    }

    public ModelAndView mostrarTodosYCrear(Request request, Response response) {
        String modo = request.queryParamOrDefault("action", "list");
        if(modo.equals("create"))
            return darAlta(request, response);
        return mostrarTodos(request, response); //por default es ?action=list
    }

    public ModelAndView mostrarTodos(Request request, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(request.params("id")));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rol", "MIEMBRO");
        parametros.put("user", miembro.getNombre() + " " + miembro.getApellido());
        parametros.put("miembroID", miembro.getId());
        parametros.put("trayectos", miembro.getTrayectos().stream().map(TrayectoMapperHBS::toDTOLazy).collect(Collectors.toList()));
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView darAlta(Request request, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(request.params("id")));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rol", "MIEMBRO");
        parametros.put("user", miembro.getNombre() + " " + miembro.getApellido());
        parametros.put("miembroID", miembro.getId());
        parametros.put("miembroLogueado", MiembroMapperHBS.toDTO(miembro));
        parametros.put("transportesTotales", generarMapeoTransportesTipos());
        parametros.put("fecha", "MES/AÑO");

        if(request.queryParams("transporte-nuevo") != null) {
            MedioDeTransporte transporte = fachadaTrayectos.obtenerTransporte(Integer.parseInt(request.queryParams("transporte-nuevo"))).get();
            TramoHBS tramoDTO = new TramoHBS();
            tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporte));
            parametros.put("tramoNuevo", tramoDTO);
            parametros.put("fecha", request.queryParams("fecha"));
        }
        return new ModelAndView(parametros, "/trayecto-edicion.hbs");
    }

    private List<Object> generarMapeoTransportesTipos() {
        List<Object> transportes = new ArrayList<>();
        List<MedioDeTransporte> transporteTotales = fachadaTrayectos.obtenerTransportes();
        transportes.add(MediosMapperHBS.toDTOLazy(TransportePublico.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTOLazy(VehiculoParticular.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTOLazy(TransporteEcologico.class, transporteTotales));
        transportes.add(MediosMapperHBS.toDTOLazy(ServicioContratado.class, transporteTotales));
        return transportes;
    }

    public Response agregar(Request request, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(request.params("id")));
        Trayecto trayecto;

        if(request.queryParams("f-trayecto-compartido-ack") != null) {
            try {
                trayecto = this.fachadaTrayectos.cargarTrayectoPasivo(miembro,
                        Integer.parseInt(request.queryParams("f-trayecto-id")));
            } catch (EntityNotFoundException e) {
                System.out.println("Creación de trayecto compartido rechazada. " +
                        "Motivo: no existe el trayecto referenciado");
                // TODO: Enviar una alerta porque no existe el trayecto referenciado
                return null;
            } catch (TrayectoConMiembrosDeDistintaOrganizacionException e) {
                System.out.println("Creación de trayecto compartido rechazada. " +
                        "Motivo: el trayecto seleccionado pertenece a un miembro de otra organizacion");
                // TODO: Enviar una alerta porque el trayecto seleccionado pertenece a un miembro de otra organizacion
                return null;
            } catch (TrayectoConMiembroRepetidoException e) {
                System.out.println("Creación de trayecto compartido rechazada. " +
                        "Motivo: el trayecto seleccionado ya pertenecía al miembro");
                // TODO: Enviar una alerta porque el trayecto seleccionado ya pertenecía al miembro
                return null;
            }
        } else {
            try {
                trayecto = this.fachadaTrayectos.cargarTrayectoActivo(miembro, request.queryMap());
            } catch (TramoSinDistanciaException e) {
                System.out.println("Creación de trayecto rechazada. " +
                        "Motivo: el trayecto seleccionado tiene la misma ubicacion inicial y final");
                // TODO: Enviar una alerta porque el trayecto tiene la misma ubicacion inicial y final
                return null;
            }
        }

        res.redirect("/miembro/" + request.params("id") + "/trayecto/" + trayecto.getId());
        return res;
    }

    public ModelAndView mostrarYEditar(Request request, Response response) {
        String modo = request.queryParamOrDefault("action", "view");
        if(modo.equals("edit"))
            return editar(request, response);
        return obtener(request, response);
    }

    public ModelAndView obtener(Request request, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(request.params("id")));
        Trayecto trayecto = fachadaTrayectos.obtenerTrayecto(Integer.parseInt(request.params("trayecto")));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rol", "miembro");
        parametros.put("user", miembro.getNombre() + " " + miembro.getApellido());
        parametros.put("trayecto", TrayectoMapperHBS.toDTO(trayecto));
        parametros.put("miembroID", miembro.getId());

        return new ModelAndView(parametros, "trayecto.hbs");
    }

    public ModelAndView editar(Request request, Response response) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(request.params("id")));
        Trayecto trayecto = fachadaTrayectos.obtenerTrayecto(Integer.parseInt(request.params("trayecto")));

        TramoHBS tramoHBS = null;

        if(request.queryParams("transporte-nuevo") != null) {
            tramoHBS = mapTramoNuevo(request);
        }

        List<TramoHBS> tramosDTO = new ArrayList<>();
        int cant = 0;
        while(request.queryParams("transporte"+cant) != null) {
            tramosDTO.add(mapTramoEditable(cant, request));
            cant++;
        }

//        String fechaActual = trayecto.getPeriodo().getMes() + "/" + trayecto.getPeriodo().getAnio();
//        String[] fecha = request.queryParamOrDefault("fecha", fechaActual).split("/");
        Periodo periodo = request.queryParams("fecha") == null ? trayecto.getPeriodo()
                : parsearPeriodo(request.queryParams("fecha"));

        TrayectoHBS trayectoDTO = new TrayectoHBS();
        if(periodo.getPeriodicidad() == 'M')
            trayectoDTO.setFecha(periodo.getMes() + "/" + periodo.getAnio().toString());
        else
            trayectoDTO.setFecha(periodo.getAnio().toString());
        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        trayectoDTO.setId(trayecto.getId());

        if(tramosDTO.isEmpty()) {
            trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTO).collect(Collectors.toList()));
        } else {
            trayectoDTO.setTramos(tramosDTO);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rol", "miembro");
        parametros.put("user", miembro.getNombre() + " " + miembro.getApellido());
        parametros.put("miembroID", miembro.getId());
        parametros.put("miembros", trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        parametros.put("transportesTotales", generarMapeoTransportesTipos());
        parametros.put("trayecto", trayectoDTO);
        parametros.put("tramoNuevo", tramoHBS);

        return new ModelAndView(parametros, "trayecto-edicion.hbs");
    }

    private TramoHBS mapTramoEditable(Integer cant, Request req) {
        MedioDeTransporte transporte = fachadaTrayectos.obtenerTransporte(Integer.parseInt(req.queryParams("transporte"+cant))).get();
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

        if(req.queryParams("tramo-id"+cant) != null)
            tramoDTO.setId(Integer.parseInt(req.queryParams("tramo-id"+cant)));

        tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporte));
        return tramoDTO;
    }

    private TramoHBS mapTramoNuevo(Request req) {

        MedioDeTransporte transporteNuevo = fachadaTrayectos.obtenerTransporte(Integer.parseInt(req.queryParams("transporte-nuevo"))).get();
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

//        parametros.put("tramoNuevo", tramoDTO);
        return tramoDTO;

    }

    public Response modificar(Request req, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(req.params("id")));
        int idTrayecto = Integer.parseInt(req.params("trayecto"));

        fachadaTrayectos.modificarTrayecto(miembro, Integer.parseInt(req.params("trayecto")), req.queryMap());

        res.redirect("/miembro/" + miembro.getId() + "/trayecto/" + idTrayecto);
        return res;
    }

    public Response eliminar(Request req, Response res) { //Elimino del trayecto, no del sistema
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("id"));
        Miembro miembro = fachadaTrayectos.obtenerMiembro(idMiembro);
        Trayecto trayecto = fachadaTrayectos.obtenerTrayecto(idTrayecto);
        miembro.quitarTrayecto(trayecto);
        trayecto.quitarMiembro(miembro);
//        res.redirect("/miembro/" + idMiembro + "/trayecto?action=list"); //redirijo desde JS
        return res;
    }


    public Response borrar(Request req, Response res) { //Elimino del sistema
        Integer idTrayecto = Integer.parseInt(req.params("id"));
        Trayecto trayecto = fachadaTrayectos.obtenerTrayecto(idTrayecto);
        trayecto.getMiembros().forEach(m -> m.quitarTrayecto(trayecto));
        trayecto.setMiembros(new ArrayList<>()); //No puedo usar foreach de miembros por problemas de concurrencia
        this.fachadaTrayectos.eliminarTrayecto(trayecto);
        return res;
    }

    private Periodo parsearPeriodo(String input) {
        Periodo periodo;
        if(input.matches("\\d+")) {
            periodo = new Periodo(Integer.parseInt(input));
        } else if(input.matches("\\d+/\\d+")) {
            String[] fecha = input.split("/"); //todo validar
            periodo = new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
        } else {
            LocalDate fecha = LocalDate.now();
            periodo = new Periodo(fecha.getYear(), fecha.getMonthValue());
        }
        return periodo;
    }
}
