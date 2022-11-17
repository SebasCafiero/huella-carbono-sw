package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.interfaces.gui.mappers.OrganizacionMapperHBS;
import ar.edu.utn.frba.dds.interfaces.gui.mappers.ReporteMapperHBS;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
import ar.edu.utn.frba.dds.entities.medibles.ReporteAgente;
import ar.edu.utn.frba.dds.entities.medibles.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaReportes;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.Repositorio;
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
        } TODO todo esto agregar una vez que tengamos la vista*/
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

    private Map<String, Object> mapUser(Request request, Response response) {
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

    public ModelAndView darAlta(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones", repoOrganizaciones.buscarTodos()); //podria omitirse y usar this
        return new ModelAndView(parametros, "reporte-creacion.hbs");
    }

    public ModelAndView darAltaYMostrar(Request request, Response response) {
        Map<String, Object> parametros;
        Organizacion org;
        ReporteOrganizacion reporte;
        int idOrg = Integer.parseInt(request.params("id"));
        try {
            org = repoOrganizaciones.buscar(idOrg); //deberia coincidir con los permisos del usuario
        } catch (NullPointerException | SessionException e) {
            parametros = new HashMap<>();
            response.status(400);
            String errorDesc = "Organización de id "+idOrg+" inexistente";
            if(e instanceof SessionException) {
                response.status(403);
                errorDesc = "Acceso no permitido";
            }
            parametros.put("codigo", response.status());
            parametros.put("descripcion", errorDesc);
            return new ErrorResponse(errorDesc).generarVista(parametros);
        }

        parametros = mapUser(request, response);
        parametros.put("organizacion", OrganizacionMapperHBS.toDTO(org));
        reporte = fachadaReportes.getReporteOrganizacion();
        if(reporte != null) {
            parametros.put("reporte", ReporteMapperHBS.toDTO(reporte));
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
            String file = org.getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";
            parametros.put("file", file);
//            parametros.put("file", "reporte"+org.getId());

            fachadaReportes.quitarReporteOrganizacion();

            //todo ver session.refresh(entity) o entityManager.refresh(entity) para no usar cache
            //todo https://sparkjava.com/documentation#examples-and-faq
        }

        return new ModelAndView(parametros, "reporte.hbs");
    }

    public Response generar1(Request request, Response response) {
        int idOrg = Integer.parseInt(request.params("id"));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrg); //todo check error
        String fechaActual = LocalDate.now().getMonthValue()+"/"+LocalDate.now().getYear();
        String[] fecha = request.queryParamOrDefault("f-fecha", fechaActual).split("/"); //todo validar fecha
        Periodo periodo = new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
        try {
            fachadaReportes.generarReporteOrganizacion2(organizacion, periodo);
            documentarReporte(fachadaReportes.getReporteOrganizacion(), organizacion); //todo no lo toma bien, agarra el viejo o ninguno
            response.redirect("/organizacion/"+organizacion.getId()+"/reporte");
        } catch (NullPointerException | FileNotFoundException | UnsupportedEncodingException e) {
            response.status(400);
            response.redirect("/error/"+400);
            return response; //todo check, no puedo retornar vista
            //return new ErrorResponse("Organización de id "+idOrg+" inexistente");
        } catch (SessionException e) { //todo check
            response.status(403);
            response.redirect("/error/"+403);
            return response;
//            return new ErrorResponse("Acceso no permitido").generarVista(parametros);
        }
        return response;
    }


    //TODO FUSIONAR DAR ALTA REPORTE
    public ModelAndView darAltaDeUnaOrganizacion(Request request, Response response) {
        if (loginController.chequearValidezAcceso(request, response, true) != null) {
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
        } TODO todo esto agregar una vez que tengamos la vista*/
        int idOrg = Integer.parseInt(request.queryParams("f-organizacion"));
        Organizacion organizacion = repoOrganizaciones.buscar(idOrg);
        LocalDate fecha = LocalDate.now();
        try {
            fachadaReportes.generarReporteOrganizacion2(organizacion, new Periodo(fecha.getYear(), fecha.getMonthValue()));
            documentarReporte(fachadaReportes.getReporteOrganizacion(), organizacion); //todo no lo toma bien, agarra el viejo o ninguno
            response.redirect("/organizacion/"+organizacion.getId()+"/reporte");
        } catch (NullPointerException | FileNotFoundException | UnsupportedEncodingException e) {
            response.status(400);
            response.redirect("/error/"+400);
            return response; //todo check, no puedo retornar vista
            //return new ErrorResponse("Organización de id "+idOrg+" inexistente");
        } catch (SessionException e) { //todo check
            response.status(403);
            response.redirect("/error/"+403);
            return response;
//            return new ErrorResponse("Acceso no permitido").generarVista(parametros);
        }
        return response;
    }

    private String documentarReporte(ReporteOrganizacion reporte, Organizacion organizacion) throws FileNotFoundException, UnsupportedEncodingException {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
        String arch = organizacion.getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";

        PrintWriter writer = new PrintWriter("resources/public/docs/"+arch, "UTF-8");

        writer.println("Fecha de Creacion: " + reporte.getFechaCreacion());
        writer.println("Periodo de Referencia: " + reporte.getPeriodoReferencia());
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

    public ModelAndView obtener(Request request, Response response) throws FileNotFoundException, UnsupportedEncodingException {
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
            String errorDesc = "Organización de id " + idOrg + " inexistente";
            if (e instanceof IndexOutOfBoundsException)
                errorDesc = "Reporte de id " + idReporte + " inexistente";
            parametros.put("descripcion", errorDesc);
            throw e;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
        String arch = organizacion.getRazonSocial().toLowerCase().replaceAll("\\s","") + reporte.getFechaCreacion().format(formato) + ".txt";

        PrintWriter writer = new PrintWriter("resources/public/docs/"+arch, "UTF-8");

        writer.println("Fecha de Creacion: " + reporte.getFechaCreacion());
        writer.println("Periodo de Referencia: " + reporte.getPeriodoReferencia());
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

        return null;
//        return arch;
    }
}
