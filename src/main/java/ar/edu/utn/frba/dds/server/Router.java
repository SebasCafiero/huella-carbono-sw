package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.OrganizacionController;
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
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        Spark.get("/", ((request, response) -> "hola gente XD"));
        OrganizacionController organizacionController = new OrganizacionController();
        Spark.get("/ejemplo/:id", organizacionController::ejemplo);
        Spark.delete("/eliminarOrganizacion/:id", organizacionController::eliminar);
        Spark.get("/mostrarTodos", organizacionController::mostrarTodos);
    }
}