package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.interfaces.RequestInvalidoApiException;
import ar.edu.utn.frba.dds.interfaces.controllers.*;
import ar.edu.utn.frba.dds.server.login.*;
import ar.edu.utn.frba.dds.server.utils.BooleanHelper;
import ar.edu.utn.frba.dds.server.utils.HandlebarsTemplateEngineBuilder;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaUsuarios;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.MiHuellaApiException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jetty.http.HttpStatus;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Router {
    private static HandlebarsTemplateEngine engine;
    private static final Gson gson = new Gson();

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

        Filter asegurarSesion = (Request request, Response response) -> {
            Integer token = Optional.ofNullable((Integer) request.session().attribute("idUsuario"))
                    .orElse(Optional.ofNullable(request.headers("Authorization"))
                            .filter(value -> value.matches("\\d{1,9}")).map(Integer::parseInt)
                            .orElseThrow(NotLoggedException::new));

            request.session().attribute("idUsuario", token);
        };

        Function<String, Filter> autorizarUsuario = tipoUsuario -> (Request request, Response response) -> {
            System.out.println(request.params().values());
            if(!request.params().values().stream().allMatch(p -> p.matches("\\d{1,9}"))) {
                throw new RequestInvalidoGuiException();
            }

            Optional.ofNullable(request.params("id"))
                    .filter(value -> value.matches("\\d{1,9}")).map(Integer::parseInt)
                    .flatMap(id -> fachadaUsuarios.findByRolId(tipoUsuario, id))
                    .filter(u -> u.getId().equals(request.session().attribute("idUsuario")))
                    .orElseThrow(UnauthorizedException::new);
        };

        Spark.path("/api", () -> {
            Spark.before("/*", (Request request, Response response) -> response.header("Content-Type", "application/json"));
            Spark.post("/login", genericController::iniciarSesionApi, gson::toJson);

            Spark.path("/organizacion", () -> {
                Spark.before("/*", asegurarSesion);

                Spark.post("", organizacionController::agregar, gson::toJson);
                Spark.get("", organizacionController::mostrarTodos, gson::toJson);

                Spark.path("/:id", () -> {
                    Spark.before("", autorizarUsuario.apply("organizacion"));
                    Spark.before("/*", autorizarUsuario.apply("organizacion"));

                    Spark.get("", organizacionController::obtener, gson::toJson);
                    Spark.put("", organizacionController::modificar);
                    Spark.delete("", organizacionController::eliminar);

                    Spark.path("/batch", () -> {
                        Spark.get("", batchMedicionController::mostrarTodos, gson::toJson);
                        Spark.get("/:batch", batchMedicionController::obtener, gson::toJson);
                        Spark.post("", batchMedicionController::agregar);
                        Spark.delete("/:batch", batchMedicionController::eliminar);
                    });

                    Spark.path("/medicion", () -> {
                        Spark.get("", medicionController::mostrarTodos);
                        Spark.get("/:id", medicionController::obtener);
                        Spark.get("/unidad/:unidad", medicionController::filtrarUnidad);
                        Spark.get("/valor/:valor", medicionController::filtrarValor);
                    });

                    Spark.path("/sector", () -> {
                        Spark.path("/:sector", () -> {
                            Spark.post("/miembro", organizacionController::agregarMiembro);
                        });
                    });

                    Spark.get("/reporte", reportesController::generarReporteOrganizacion);
                });
            });

            Spark.path("/miembro", () -> {
                Spark.before("/*", asegurarSesion);

                Spark.get("", miembroController::mostrarTodos);
                Spark.post("", miembroController::agregar, gson::toJson);

                Spark.path("/:id", () -> {
                    Spark.before("", autorizarUsuario.apply("miembro"));
                    Spark.before("/*", autorizarUsuario.apply("miembro"));

//                    Spark.get("", miembroController::obtener, engine); TODO
                    Spark.delete("", miembroController::eliminar);
                    Spark.put("", miembroController::modificar);
                });

            });

            Spark.path("/agente", () -> {
                Spark.before("/*", asegurarSesion);

                Spark.get("", agenteSectorialController::mostrarTodos);
                Spark.post("", agenteSectorialController::agregar);

                Spark.path("/:id", () -> {
                    Spark.before("", autorizarUsuario.apply("agenteSectorial"));
                    Spark.before("/*", autorizarUsuario.apply("agenteSectorial"));

                    Spark.get("", agenteSectorialController::obtener);
                    Spark.delete("", agenteSectorialController::eliminar);
                    Spark.put("", agenteSectorialController::modificar);

                    Spark.get("/reporte", reportesController::generarReporteAgente);
                });
            });

            Spark.path("/factorEmision", () -> {
                Spark.get("", factorEmisionController::mostrarTodos);
                Spark.post("", factorEmisionController::modificar);
            });

            Spark.delete("trayecto/:id", trayectosController::borrar); //Para eliminar definitivamente el trayecto (admin)
        });


        /* RUTAS PARA LA GUI */

        Spark.post("/login", genericController::iniciarSesion);
        Spark.post("/logout", genericController::cerrarSesion);
        Spark.post("/user", genericController::altaUsuario); //todo
        Spark.get("/home", genericController::inicio, engine);
        Spark.get("/menu", genericController::menu, engine);

        Spark.path("/miembro/:id", () -> {
            Spark.before("", autorizarUsuario.apply("miembro"));
            Spark.before("/*", autorizarUsuario.apply("miembro"));

            Spark.path("/trayecto", () -> {
                Spark.get("", trayectosController::mostrarTodosYCrear, engine);
                Spark.post("", trayectosController::agregar);

                Spark.path("/:trayecto", () -> {
                    Spark.get("", trayectosController::mostrarYEditar, engine);
                    Spark.post("", trayectosController::modificar);
                    Spark.delete("", trayectosController::eliminar);
                });
            });
        });

        Spark.path("/organizacion/:id", () -> {
            Spark.before("", autorizarUsuario.apply("organizacion"));
            Spark.before("/*", autorizarUsuario.apply("organizacion"));

            Spark.get("/reporte", reportesController::darAltaYMostrarOrg, engine);
            Spark.post("/reporte", reportesController::generarOrg);
        });

        Spark.path("/agente/:id", () -> {
            Spark.before("", autorizarUsuario.apply("agenteSectorial"));
            Spark.before("/*", autorizarUsuario.apply("agenteSectorial"));

            Spark.get("/organizacion", agenteSectorialController::mostrarOrganizaciones, engine);
            Spark.get("/reporte", reportesController::darAltaYMostrarAgente, engine);
            Spark.post("/reporte", reportesController::generarAgente);
        });

        Spark.get("/*", ((request, response) -> {
            response.redirect("/home");
            response.status(HttpStatus.NOT_FOUND_404);
            return response;
        }));
    }

    private static void configureExceptions() {
        Spark.exception(NotLoggedException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            System.out.println("Ip origen: " + request.ip());
            response.status(HttpStatus.UNAUTHORIZED_401);
            if(request.pathInfo().startsWith("/api")) {
                response.body(gson.toJson(exception.getMessage()));
            } else {
                response.body(exception.getMessage());
            }
            response.redirect("/home");
        });

        Spark.exception(UnauthorizedException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            response.status(HttpStatus.FORBIDDEN_403);
            if(request.pathInfo().startsWith("/api")) {
                response.body(gson.toJson(exception.getMessage()));
            } else {
                response.body(exception.getMessage());
            }
            response.redirect("/menu");
        });

        Spark.exception(AuthenticationException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            response.status(HttpStatus.UNAUTHORIZED_401);
            if(request.pathInfo().startsWith("/api")) {
                response.body(gson.toJson(exception.getMessage()));
            } else {
                response.body(exception.getMessage());
            }
            response.redirect("/home"); // TODO: Mostrar alerta de error
        });

        Spark.exception(MiHuellaApiException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            response.status(HttpStatus.CONFLICT_409);
            response.body(gson.toJson(exception.getError()));
        });

        Spark.exception(RequestInvalidoApiException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + exception.getMessage());
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(gson.toJson("Request inválido: " + exception.getMessage()));
        });

        Spark.exception(RequestInvalidoGuiException.class, (exception, request, response) -> {
            System.out.println("Request rechazado. " + "Los parametros de ruta deben ser enteros");
            response.status(HttpStatus.BAD_REQUEST_400);
            response.redirect("/menu");
        });
    }
}