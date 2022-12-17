package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.exceptions.TrayectoConMiembroRepetidoException;
import ar.edu.utn.frba.dds.interfaces.gui.GuiUtils;
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

        Map<String, Object> parametros = GuiUtils.dtoHeader(request);
        parametros.put("miembroID", miembro.getId());
        parametros.put("trayectos", miembro.getTrayectos().stream().map(TrayectoMapperHBS::toDTOLazy).collect(Collectors.toList()));
        return new ModelAndView(parametros, "trayectos.hbs");
    }

    public ModelAndView darAlta(Request request, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(request.params("id")));

        Map<String, Object> parametros = GuiUtils.dtoHeader(request);
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
        return new ModelAndView(parametros, "trayecto-edicion.hbs");
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

        Map<String, Object> parametros = GuiUtils.dtoHeader(request);
        parametros.put("trayecto", TrayectoMapperHBS.toDTO(trayecto));
        parametros.put("miembroID", miembro.getId());

        return new ModelAndView(parametros, "trayecto.hbs");
    }

    public ModelAndView editar(Request request, Response response) {
        Trayecto trayecto = fachadaTrayectos.obtenerTrayecto(Integer.parseInt(request.params("trayecto")));

        TramoHBS tramoNuevoDTO = null;
        if(request.queryParams("transporte-nuevo") != null) {
            MedioDeTransporte transporteNuevo = fachadaTrayectos.obtenerTransporte(Integer.parseInt(request.queryParams("transporte-nuevo"))).get();
            tramoNuevoDTO = TramoMapperHBS.toDTONuevoEditable(transporteNuevo, request);
        }

        TrayectoHBS trayectoDTO = TrayectoMapperHBS.toDTOEditable(trayecto, request);
        List<TramoHBS> tramosDTO = new ArrayList<>();
        int cant = 0;
        while(request.queryParams("transporte"+cant) != null) {
            MedioDeTransporte transporte = fachadaTrayectos.obtenerTransporte(Integer.parseInt(request.queryParams("transporte"+cant))).get();
            tramosDTO.add(TramoMapperHBS.toDTOEditado(transporte, cant, request));
            cant++;
        }
        if(tramosDTO.isEmpty()) //No hubo modificaciones intermedias
            trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTO).collect(Collectors.toList()));
        else
            trayectoDTO.setTramos(tramosDTO);

        Map<String, Object> parametros = GuiUtils.dtoHeader(request);
        parametros.put("miembroID", Integer.parseInt(request.params("id")));
        parametros.put("miembros", trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        parametros.put("transportesTotales", generarMapeoTransportesTipos());
        parametros.put("trayecto", trayectoDTO);
        parametros.put("tramoNuevo", tramoNuevoDTO);

        return new ModelAndView(parametros, "trayecto-edicion.hbs");
    }

    public Response modificar(Request req, Response res) {
        Miembro miembro = fachadaTrayectos.obtenerMiembro(Integer.parseInt(req.params("id")));
        int idTrayecto = Integer.parseInt(req.params("trayecto"));

        fachadaTrayectos.modificarTrayecto(miembro, idTrayecto, req.queryMap());

        res.redirect("/miembro/" + miembro.getId() + "/trayecto/" + idTrayecto);
        return res;
    }

    public Response eliminar(Request req, Response res) { //Elimino del trayecto, no del sistema
        Integer idTrayecto = Integer.parseInt(req.params("trayecto"));
        Integer idMiembro = Integer.parseInt(req.params("id"));
        this.fachadaTrayectos.quitarTrayectoDeMiembro(idMiembro, idTrayecto);
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


}
