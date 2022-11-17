package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.server.login.*;
import ar.edu.utn.frba.dds.server.utils.BooleanHelper;
import ar.edu.utn.frba.dds.server.utils.HandlebarsTemplateEngineBuilder;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine(); //Para diseño web
        Spark.staticFileLocation("/public"); //Para diseño web
        Router.configure();
    }

    private static void configure() {
        UserUtils userUtils = new UserUtils();

        Spark.get("/", ((request, response) -> "Api HC DDS - 2022"));

        LoginController loginController = new LoginController();
        Spark.post("/login", (request, response) -> {
            User realUser = userUtils.buscarUsuarioEnRepo(request.queryParams("username"), request.queryParams("password"))
                    .orElseThrow(AuthenticationException::new);

            HashMap<String, Object> user = new HashMap<>();
            user.put("user", realUser.getUsername());
            user.put("rol", realUser.getRol());
            user.put("miembro", realUser.getMiembro() != null ? realUser.getMiembro().getId() : null);
            user.put("organizacion", realUser.getOrganizacion() != null ? realUser.getOrganizacion().getId() : null);
            user.put("agente", realUser.getAgenteSectorial() != null ? realUser.getOrganizacion().getId() : null);
            return new ModelAndView(user, "menu.hbs");
        }, engine);
        Spark.post("/logout", loginController::cerrarSesion);
        Spark.post("/errorAcceso", loginController::errorAcceso);

        UserController userController = new UserController();
        Spark.post("/login/alta", userController::agregar);



        OrganizacionController organizacionController = new OrganizacionController();

        Spark.path("/organizacion", () -> {
//            List<Rol> rolesValidos;
            Spark.before("/*", (Request request, Response response) -> {
                if (!userUtils.estaLogueado(request))
                    throw new NotLoggedException();
            });
            Spark.post("", organizacionController::agregar);
            Spark.get("", organizacionController::mostrarTodos);


            Spark.path("/:id", () -> {
                Spark.before("/*", (Request request, Response response) -> {
                    User user = userUtils.getUsuarioLogueado(request.session().attribute("idUsuario"))
                            .orElseThrow(AuthenticationException::new);

                    if(!user.isOrganizacion() || !user.getId().equals(Integer.parseInt(request.params("id")))) {
                        throw new UnauthorizedException();
                    }
                });

                Spark.get("", organizacionController::obtener);
                Spark.put("", organizacionController::modificar);
                Spark.delete("", organizacionController::eliminar);
            });

        });

        Spark.exception(NotLoggedException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            System.out.println("Ip origen: " + request.ip());
            response.redirect("/home");
        });

//        Spark.get("/organizacion/:id", organizacionController::obtener);
//        Spark.delete("/organizacion/:id", organizacionController::eliminar);
//        Spark.get("/organizacion", organizacionController::mostrarTodos);
//        Spark.put("/organizacion/:id", organizacionController::modificar);
//        Spark.post("/organizacion", organizacionController::agregar);

        MiembroController miembroController = new MiembroController();
        Spark.get("/miembro/:id", miembroController::obtener, engine);
        Spark.delete("/miembro/:id", miembroController::eliminar);
        Spark.get("/miembro", miembroController::mostrarTodos);
        Spark.put("/miembro/:id", miembroController::modificar);
        Spark.post("/miembro", miembroController::agregar);

        MedicionController medicionController = new MedicionController();
        Spark.get("/medicion/:id", medicionController::obtener);
        Spark.get("/medicion", medicionController::mostrarTodos);
        Spark.get("/medicion/unidad/:unidad",medicionController::filtrarUnidad);
        Spark.get("/medicion/valor/:valor",medicionController::filtrarValor);

        BatchMedicionController batchMedicionController = new BatchMedicionController();
        Spark.get("/batchMedicion", batchMedicionController::mostrarTodos);
        Spark.get("/batchMedicion/:id", batchMedicionController::obtener);
        Spark.post("/batchMedicion", batchMedicionController::agregar);
        Spark.delete("/batchMedicion/:id", batchMedicionController::eliminar);

        FactorEmisionController factorEmisionController = new FactorEmisionController();
        Spark.put("/factorEmision/:id", factorEmisionController :: modificar);
        Spark.get("/factorEmision", factorEmisionController :: mostrarTodos); // solo para probar

        AgenteSectorialController agenteSectorialController = new AgenteSectorialController();
        Spark.get("/agenteSectorial/:id", agenteSectorialController::obtener);
        Spark.get("/agenteSectorial", agenteSectorialController::mostrarTodos);
        Spark.delete("/agenteSectorial/:id", agenteSectorialController::eliminar);
        Spark.put("/agenteSectorial/:id", agenteSectorialController::modificar);
        Spark.post("/agenteSectorial", agenteSectorialController::agregar);

        ReportesController reportesController = new ReportesController();
        Spark.get("/reportes/agente/:id", reportesController::generarReporteAgente);
        Spark.get("/reportes/organizacion/:id", reportesController::generarReporteOrganizacion);

        /* Endpoints para la GUI */
        HomeController homeController = new HomeController();
//        LoginController loginController = new LoginController();
        TrayectosController trayectosController = new TrayectosController();

        Spark.get("/home", homeController::inicio, engine);
        Spark.get("/menu", homeController::menu, engine);

        Spark.post("/login", loginController::intentarLogear);
        Spark.post("/login/alta", loginController::agregar);
//        Spark.post("/logout", loginController::desloguear);

        Spark.get("/organizacion/:id/reporte", reportesController::darAltaYMostrar, engine);
        Spark.post("/organizacion/:id/reporte", reportesController::generar);

        Spark.get("/miembro/:id/trayecto", trayectosController::mostrarTodosYCrear, engine);
        Spark.post("/miembro/:id/trayecto", trayectosController::agregar);
        Spark.get("/miembro/:miembro/trayecto/:trayecto", trayectosController::mostrarYEditar, engine); //TODO NO VALIDA EL MIEMBRO
        Spark.post("/miembro/:miembro/trayecto/:trayecto", trayectosController::modificar);
        Spark.delete("/miembro/:miembro/trayecto/:trayecto", trayectosController::eliminar);

        Spark.delete("trayecto/:id", trayectosController::borrar); //Para eliminar definitivamente el trayecto (admin)

    }
}