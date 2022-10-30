package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.login.LoginController;
import ar.edu.utn.frba.dds.mihuella.dto.ErrorResponse;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import org.hibernate.SessionException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;

public class ReportesController {
    private FachadaReportes fachadaReportes;
    private Repositorio<AreaSectorial> repoAreas;
    private Repositorio<Organizacion> repoOrganizaciones;
    private LoginController loginController;

    public ReportesController() {
        this.fachadaReportes = new FachadaReportes();
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.loginController = new LoginController();
    }

    public Object generarReporteAgente(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        int idArea = Integer.parseInt(request.params(("id")));
        AreaSectorial areaSectorial = repoAreas.buscar(idArea);
        if(areaSectorial == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + idArea + " no existe");
        }

        LocalDate fecha = LocalDate.now();
        ReporteAgente reporte = fachadaReportes
                .generarReporteAgente(areaSectorial, fecha.getYear(), fecha.getMonthValue());
        return reporte;
    }

    public Object generarReporteOrganizacion(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        int idOrganizacion = Integer.parseInt(request.params(("id")));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrganizacion);
        if(organizacion == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + idOrganizacion + " no existe");
        }

        LocalDate fecha = LocalDate.now();
        ReporteOrganizacion reporte = fachadaReportes
                .generarReporteOrganizacion(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
        return reporte;
    }




    public ModelAndView darAlta(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones", repoOrganizaciones.buscarTodos()); //podria omitirse y usar this
        return new ModelAndView(parametros, "reporte-creacion.hbs");
    }
    //TODO FUSIONAR DAR ALTA REPORTE
    public ModelAndView darAltaDeUnaOrganizacion(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }
        Map<String, Object> parametros = new HashMap<>();
        Organizacion org = repoOrganizaciones.buscar(Integer.parseInt(request.params("id")));
        parametros.put("organizaciones", Arrays.asList(org));
        return new ModelAndView(parametros, "reporte-creacion.hbs");
    }

    public Response generar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        int idOrg = Integer.parseInt(request.queryParams("f-organizacion"));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrg);
        LocalDate fecha = LocalDate.now();
        try {
            ReporteOrganizacion reporte = fachadaReportes
                    .generarReporteOrganizacion(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
            response.redirect("/organizacion/"+organizacion.getId()+"/reporte/"+reporte.getId());
        } catch (NullPointerException e) {
            response.status(400);
            response.redirect("/error/"+400);
            return response; //todo check, no puedo retornar vista
            //return new ErrorResponse("Organización de id "+idOrg+" inexistente");
        }
        return response;
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        Map<String, Object> parametros = new HashMap<>();
        List<Map<String, Object>> orgs = new ArrayList<>();
        repoOrganizaciones.buscarTodos().forEach(org -> {
           Map<String, Object> repoMap = new HashMap<>();
           repoMap.put("id", org.getId());
           repoMap.put("razon", org.getRazonSocial());
           repoMap.put("reportes", org.getReportes());
           orgs.add(repoMap);
        });
        parametros.put("organizaciones", orgs); //podria omitirse y usar this
        return new ModelAndView(parametros, "reportes.hbs");
    }
    //TODO FUSIONAR MOSTRAR TODOS
    public ModelAndView mostrarTodosDeUnaOrganizacion(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        Map<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = repoOrganizaciones.buscar(Integer.parseInt(request.params("id")));
        List<Map<String, Object>> orgs = new ArrayList<>(); //para reutilizar #mostrarTodos
        Map<String, Object> parametrosOrg = new HashMap<>();
        parametrosOrg.put("id", organizacion.getId());
        parametrosOrg.put("razon", organizacion.getRazonSocial());
        parametrosOrg.put("reportes", organizacion.getReportes());
        orgs.add(parametrosOrg);
        parametros.put("organizaciones", orgs);
        return new ModelAndView(parametros, "reportes.hbs");
    }

    public ModelAndView obtener(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        int idOrg = Integer.parseInt(request.params("org"));
        Organizacion organizacion;
        int idReporte = Integer.parseInt(request.params("rep"));
        ReporteOrganizacion reporte;
        Map<String, Object> parametros = new HashMap<>();
        try {
            organizacion = repoOrganizaciones.buscar(idOrg);
            reporte = organizacion.getReportes().get(idReporte);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            response.status(400);
            parametros.put("codigo", response.status());
            String errorDesc = "Organización de id "+idOrg+" inexistente";
            if(e instanceof IndexOutOfBoundsException)
                errorDesc = "Reporte de id "+idReporte+" inexistente";
            parametros.put("descripcion", errorDesc);

            return new ErrorResponse(errorDesc).generarVista(parametros);
        } catch (SessionException e) { //todo check
            response.status(403);
            parametros.put("codigo", response.status());
            parametros.put("descripcion", "Acceso no permitido");
            return new ErrorResponse("Acceso no permitido").generarVista(parametros);
        }
        parametros.put("organizacion", organizacion);
        parametros.put("reporte", reporte);
        return new ModelAndView(parametros, "reporte.hbs");
    }

    //todo al mostrar un reporte en detalle, poner como fue sumando cada medicion
}
