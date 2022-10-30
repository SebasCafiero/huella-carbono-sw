package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.login.LoginController;
import ar.edu.utn.frba.dds.spark.utils.BooleanHelper;
import ar.edu.utn.frba.dds.spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

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

    private static void configure(){
        Spark.get("/", ((request, response) -> "Api HC DDS - 2022"));

        OrganizacionController organizacionController = new OrganizacionController();
        Spark.get("/organizacion/:id", organizacionController::obtener);
        Spark.delete("/organizacion/:id", organizacionController::eliminar);
        Spark.get("/organizacion", organizacionController::mostrarTodos);
        Spark.put("/organizacion/:id", organizacionController::modificar);
        Spark.post("/organizacion", organizacionController::agregar);

        MiembroController miembroController = new MiembroController();
        Spark.get("/miembro/:id", miembroController::obtener);
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
        Spark.get("/factorEmision",factorEmisionController :: mostrarTodos); // solo para probar

        AgenteSectorialController agenteSectorialController = new AgenteSectorialController();
        Spark.get("/agenteSectorial/:id", agenteSectorialController::obtener);
        Spark.get("/agenteSectorial", agenteSectorialController::mostrarTodos);
        Spark.delete("/agenteSectorial/:id", agenteSectorialController::eliminar);
        Spark.put("/agenteSectorial/:id", agenteSectorialController::modificar);
        Spark.post("/agenteSectorial", agenteSectorialController::agregar);

        ReportesController reportesController = new ReportesController();
        Spark.get("/reportes/agente/:id", reportesController::generarReporteAgente);
        Spark.get("/reportes/organizacion/:id", reportesController::generarReporteOrganizacion);

        LoginController loginController = new LoginController();
        Spark.post("/login", loginController::intentarLogear);
        Spark.post("/logout", loginController::cerrarSesion);
        Spark.post("/errorAcceso", loginController::errorAcceso);

        UserController userController = new UserController();
        Spark.post("/login/alta", userController::agregar);

        TrayectosController trayectosController = new TrayectosController();
        Spark.get("/trayectos", trayectosController::mostrarTodos, engine); //OK

        Spark.get("/trayecto/:id", trayectosController::obtener, engine); //OK
        Spark.post("/trayecto/:id", trayectosController::modificar); //CHECK
        Spark.delete("/trayecto/:id", trayectosController::eliminar); //CHECK

        Spark.get("/trayecto", trayectosController::darAlta, engine); //OK
        Spark.post("/trayecto", trayectosController::agregar); //OK


        HomeController homeController = new HomeController();
        Spark.get("/home", homeController::inicio, engine);
        Spark.get("/menu", homeController::menu, engine );
        Spark.get("/logout", homeController::inicio, engine);

        Spark.get("/reporte", reportesController::darAlta, engine); //CHECK
        Spark.post("/reporte", reportesController::generar); //CHECK
        Spark.get("/reportes", reportesController::mostrarTodos, engine); //CHECK
        Spark.get("/organizacion/:id/reportes", reportesController::mostrarTodosDeUnaOrganizacion, engine); //CHECK
        Spark.get("/organizacion/:org/reporte/:rep", reportesController::obtener, engine); //OK
        Spark.get("/organizacion/:id/reporte", reportesController::darAltaDeUnaOrganizacion, engine); //CHECK

        Spark.get("/reiniciar", homeController::reiniciar);
    }
}