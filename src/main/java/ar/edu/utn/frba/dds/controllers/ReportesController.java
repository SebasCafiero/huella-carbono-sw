package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.mihuella.dto.ErrorResponse;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportesController {
    private FachadaReportes fachadaReportes;
    private Repositorio<AreaSectorial> repoAreas;
    private Repositorio<Organizacion> repoOrganizaciones;

    public ReportesController() {
        this.fachadaReportes = new FachadaReportes();
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
    }

    public Object generarReporteAgente(Request request, Response response) {
        int idArea = Integer.parseInt(request.params(("id")));
        AreaSectorial areaSectorial = repoAreas.buscar(idArea);
        if(areaSectorial == null) {
            return new ErrorResponse("La organizacion de id " + idArea + " no existe");
        }

        LocalDate fecha = LocalDate.now();
        ReporteAgente reporte = fachadaReportes
                .generarReporteAgente(areaSectorial, fecha.getYear(), fecha.getMonthValue());
        return reporte;
    }

    public Object generarReporteOrganizacion(Request request, Response response) {
        int idOrganizacion = Integer.parseInt(request.params(("id")));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrganizacion);
        if(organizacion == null) {
            return new ErrorResponse("La organizacion de id " + idOrganizacion + " no existe");
        }

        LocalDate fecha = LocalDate.now();
        ReporteOrganizacion reporte = fachadaReportes
                .generarReporteOrganizacion(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
        return reporte;
    }




    public ModelAndView darAlta(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones", repoOrganizaciones.buscarTodos()); //podria omitirse y usar this
        return new ModelAndView(parametros, "reporte-creacion.hbs");
    }

    public Response generar(Request request, Response response) {
        Organizacion organizacion = repoOrganizaciones.buscar(Integer.parseInt(request.queryParams("f-organizacion")));
        if(organizacion == null) {
            //return new ErrorResponse("La organizacion no existe");
        }
        LocalDate fecha = LocalDate.now();
        ReporteOrganizacion reporte = fachadaReportes
                .generarReporteOrganizacion(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
        response.redirect("/organizacion/"+organizacion.getId()+"/reporte/"+reporte.getId()); //todo check
        return response;
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
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

    public ModelAndView obtener(Request request, Response response) {
        Organizacion organizacion = repoOrganizaciones.buscar(Integer.parseInt(request.params("org")));
        Integer idReporte = Integer.parseInt(request.params("rep"));
        ReporteOrganizacion reporte;
        try {
            reporte = organizacion.getReportes().get(idReporte);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Reporte inexistente"); //todo
        }
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", organizacion);
        parametros.put("reporte", reporte);

        return new ModelAndView(parametros, "reporte.hbs");
    }

    //todo al mostrar un reporte en detalle, poner como fue sumando cada medicion
}
