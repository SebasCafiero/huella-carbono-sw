package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.server.login.*;
import ar.edu.utn.frba.dds.server.utils.BooleanHelper;
import ar.edu.utn.frba.dds.server.utils.HandlebarsTemplateEngineBuilder;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaUsuarios;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.function.Function;

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
        Router.configureExceptions();
    }

    private static void configure() {
        FachadaUsuarios fachadaUsuarios = new FachadaUsuarios();
        MedicionController medicionController = new MedicionController();
        BatchMedicionController batchMedicionController = new BatchMedicionController();
        FactorEmisionController factorEmisionController = new FactorEmisionController();
        AgenteSectorialController agenteSectorialController = new AgenteSectorialController();
        ReportesController reportesController = new ReportesController();
        MiembroController miembroController = new MiembroController();
        OrganizacionController organizacionController = new OrganizacionController();
        GenericController genericController = new GenericController();
        TrayectosController trayectosController = new TrayectosController();

        Function<Function<User, Boolean>, Filter> autorizarUsuario =
                tipoUsuario -> (Request request, Response response) -> {
            User user = fachadaUsuarios.findById(request.session().attribute("idUsuario"))
                    .orElseThrow(AuthenticationException::new);

            if(!tipoUsuario.apply(user) || !user.getIdRol().equals(Integer.parseInt(request.params("id")))) {
                throw new UnauthorizedException();
            }
        };

        Spark.path("/api", () -> {

            Spark.path("/organizacion", () -> {
                Spark.before("/*", (Request request, Response response) -> {
                    if (!fachadaUsuarios.estaLogueado(request))
                        throw new NotLoggedException();
                });

                Spark.post("", organizacionController::agregar);
                Spark.get("", organizacionController::mostrarTodos);

                Spark.path("/:id", () -> {
                    Spark.before("/*", autorizarUsuario.apply(User::isOrganizacion));
                    Spark.get("", organizacionController::obtener);
                    Spark.put("", organizacionController::modificar);
                    Spark.delete("", organizacionController::eliminar);

                });
            });

            Spark.path("/miembro", () -> {
                Spark.get("/:id", miembroController::obtener, engine);
                Spark.delete("/:id", miembroController::eliminar);
                Spark.get("", miembroController::mostrarTodos);
                Spark.put("/:id", miembroController::modificar);
                Spark.post("", miembroController::agregar);
            });

            Spark.path("/agenteSectorial", () -> {
                Spark.get("/:id", agenteSectorialController::obtener);
                Spark.delete("/:id", agenteSectorialController::eliminar);
                Spark.put("/:id", agenteSectorialController::modificar);
                Spark.get("", agenteSectorialController::mostrarTodos);
                Spark.post("", agenteSectorialController::agregar);
            });

            Spark.path("/batchMedicion", () -> {
                Spark.get("", batchMedicionController::mostrarTodos);
                Spark.get("/:id", batchMedicionController::obtener);
                Spark.post("", batchMedicionController::agregar);
                Spark.delete("/:id", batchMedicionController::eliminar);
            });

            Spark.path("/medicion", () -> {
                Spark.get("", medicionController::mostrarTodos);
                Spark.get("/:id", medicionController::obtener);
                Spark.get("/unidad/:unidad", medicionController::filtrarUnidad);
                Spark.get("/valor/:valor", medicionController::filtrarValor);
            });

            Spark.put("/factorEmision/:id", factorEmisionController :: modificar);
            Spark.get("/factorEmision", factorEmisionController :: mostrarTodos); // solo para probar

            Spark.get("/reportes/agente/:id", reportesController::generarReporteAgente);
            Spark.get("/reportes/organizacion/:id", reportesController::generarReporteOrganizacion);

            Spark.delete("trayecto/:id", trayectosController::borrar); //Para eliminar definitivamente el trayecto (admin)
        });


        /* RUTAS PARA LA GUI */

        Spark.post("/login", genericController::iniciarSesion);
        Spark.post("/logout", genericController::cerrarSesion);
        Spark.post("/user", genericController::altaUsuario); //todo
        Spark.get("/home", genericController::inicio, engine);
        Spark.get("/menu", genericController::menu, engine);

        Spark.path("/miembro/:id", () -> {
            Spark.before("/*", autorizarUsuario.apply(User::isMiembro));
            Spark.get("/trayecto", trayectosController::mostrarTodosYCrear, engine);
            Spark.post("/trayecto", trayectosController::agregar);
            Spark.get("/trayecto/:trayecto", trayectosController::mostrarYEditar, engine);
            Spark.post("/trayecto/:trayecto", trayectosController::modificar);
            Spark.delete("/trayecto/:trayecto", trayectosController::eliminar);
        });

        Spark.get("/organizacion/:organizacion/reporte", reportesController::darAltaYMostrar, engine);
        Spark.post("/organizacion/:organizacion/reporte", reportesController::generar);

        Spark.get("/agente/:id/organizacion", agenteSectorialController::mostrarOrganizaciones, engine);
        Spark.get("/agente/:agente/reporte", reportesController::darAltaYMostrar, engine);
        Spark.post("/agente/:agente/reporte", reportesController::generar);

        Spark.get("/*", ((request, response) -> {
            response.redirect("/home");
            return response;
        }));
    }

    private static void configureExceptions() {
        Spark.exception(NotLoggedException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            System.out.println("Ip origen: " + request.ip());
            response.redirect("/home");
        });

        Spark.exception(UnauthorizedException.class, (exception, request, response) -> {

        });
    }
}