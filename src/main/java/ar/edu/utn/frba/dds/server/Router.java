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



        /* Endpoints para la GUI */
        HomeController homeController = new HomeController();
        LoginController loginController = new LoginController();
        TrayectosController trayectosController = new TrayectosController();

        Spark.get("/home", homeController::inicio, engine);
        Spark.get("/menu", homeController::menu, engine );

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