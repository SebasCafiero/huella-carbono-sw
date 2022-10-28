package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.mihuella.dto.ErrorResponse;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.server.SystemProperties;
import org.hibernate.SessionException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            response.status(400);
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
            response.status(400);
            return new ErrorResponse("La organizacion de id " + idOrganizacion + " no existe");
        }

        LocalDate fecha = LocalDate.now();
        ReporteOrganizacion reporte = fachadaReportes
                .generarReporteOrganizacion(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
        return reporte;
    }






    public Map<String, Object> mapUser(Request request, Response response) {
        String username = request.session().attribute("currentUser");
//        User user = new UserUtils().buscar(username);
        Map<String, Object> parametros = new HashMap<>();

        int id;
//        id = user.getRolId(); // id de la org
        id = 2;

        String rol;
//        rol = user.getRol(); //organizacion
        rol = "organizacion";

        String name;
//        name = user.getName();
        name = "Fifa";

        parametros.put("rol", rol.toUpperCase(Locale.ROOT));
        parametros.put(rol, id);
        parametros.put("user", name);
        return parametros;
    }

    public ModelAndView darAltaYMostrar(Request request, Response response) {
        Map<String, Object> parametros;
        Organizacion org;
        ReporteOrganizacion reporte;
        int idOrg = Integer.parseInt(request.params("id"));
        try {
            org = repoOrganizaciones.buscar(idOrg); //deberia coincidir con los permisos del usuario
        } catch (NullPointerException e) {
            response.status(400);
            parametros = new HashMap<>();
            parametros.put("codigo", response.status());
            String errorDesc = "Organización de id "+idOrg+" inexistente";
            parametros.put("descripcion", errorDesc);
            return new ErrorResponse(errorDesc).generarVista(parametros);
        }

        parametros = mapUser(request, response);
        parametros.put("razonSocial", org.getRazonSocial());
        parametros.put("orgID", org.getId());

        reporte = fachadaReportes.getReporteOrganizacion();
        if(reporte != null){
            parametros.put("reporte", mapeoReporte(reporte));
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
            String file = org.getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";
            parametros.put("file", file);
//            parametros.put("file", "reporte"+org.getId());
        }

//        List<Map<String, Object>> meses = new ArrayList<>(); //todo

        return new ModelAndView(parametros, "reporte.hbs");
    }

    public Map<String, Object> mapeoReporte(ReporteOrganizacion reporte) {
        Map<String, Object> reporteMap = new HashMap<>();
        reporteMap.put("fecha", reporte.getFechaCreacion());
        reporteMap.put("consumoTotal", reporte.getConsumoTotal());
        reporteMap.put("consumoMediciones", reporte.getConsumoMediciones());
        reporteMap.put("consumoTrayectos", reporte.getConsumoTotal()-reporte.getConsumoMediciones());
        reporteMap.put("consumoPorCategoria", reporte.getConsumoPorCategoria());
        reporteMap.put("consumoPorSector", reporte.getConsumoPorSector());
        reporteMap.put("consumoPorMiembro", reporte.getConsumoPorMiembro());
        return reporteMap;
    }

    public Response generar(Request request, Response response) {
        int idOrg = Integer.parseInt(request.params("id"));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrg); //todo check error
        LocalDate fecha = LocalDate.now();//todo
        Periodo periodo = new Periodo(2020,5);
        try {
            fachadaReportes.generarReporteOrganizacion2(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
            documentarReporte(fachadaReportes.getReporteOrganizacion(), organizacion); //todo no lo toma bien, agarra el viejo o ninguno
            response.redirect("/organizacion/"+organizacion.getId()+"/reporte");
        } catch (NullPointerException | FileNotFoundException | UnsupportedEncodingException e) {
            response.status(400);
            response.redirect("/error/"+400);
            return response; //todo check, no puedo retornar vista
            //return new ErrorResponse("Organización de id "+idOrg+" inexistente");
        }
        return response;
    }

    public String documentarReporte(ReporteOrganizacion reporte, Organizacion organizacion) throws FileNotFoundException, UnsupportedEncodingException {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
        String arch = organizacion.getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";

        PrintWriter writer = new PrintWriter("resources/public/docs/"+arch, "UTF-8");

        writer.println("Total : " + reporte.getConsumoTotal());
        writer.println("Total mediciones : " + reporte.getConsumoMediciones());

        for (Map.Entry<Miembro, Float> miembro : reporte.getConsumoPorMiembro().entrySet()) {
            writer.println("Miembro : " + miembro.getKey().getNroDocumento() + " :-> " + miembro.getValue());
        }

        for (Map.Entry<Sector, Float> sector : reporte.getConsumoPorSector().entrySet()) {
            writer.println("Sector : " + sector.getKey().getNombre() + " :-> " + sector.getValue());
        }

        for(Map.Entry<Categoria, Float> categoria : reporte.getConsumoPorCategoria().entrySet()) {
            writer.println("Categoria : " + categoria.getKey().toString() + " :-> " + categoria.getValue());
        }

        writer.close();

        return arch;
    }



    public ModelAndView obtener(Request request, Response response) {
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
