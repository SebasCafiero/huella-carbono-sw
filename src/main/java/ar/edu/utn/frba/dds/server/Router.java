package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.BatchMedicionController;
import ar.edu.utn.frba.dds.controllers.FactorEmisionController;
import ar.edu.utn.frba.dds.controllers.MedicionController;
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
//        Spark.staticFileLocation("/public");
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
        MedicionController medicionController = new MedicionController();
        Spark.get("/medicion/:id", medicionController::obtener);
        Spark.get("/medicion", medicionController::mostrarTodos);
        Spark.get("/medicion/unidad/:unidad",medicionController::filtrarUnidad);
        Spark.get("/medicion/valor/:valor",medicionController::filtrarValor);
        BatchMedicionController batchMedicionController = new BatchMedicionController();
        Spark.get("/batchMedicion/:id", batchMedicionController::obtener);
        Spark.delete("/batchMedicion/:id", batchMedicionController::eliminar);
        Spark.get("/batchMedicion", batchMedicionController::mostrarTodos);
        Spark.put("/batchMedicion/:id", batchMedicionController::modificar);
        Spark.post("/batchMedicion", batchMedicionController::agregar);
        FactorEmisionController factorEmisionController = new FactorEmisionController();
        Spark.put("/factorEmision/:id", factorEmisionController :: modificar);
        Spark.get("/factorEmision",factorEmisionController :: mostrarTodos); // solo para probar
    }
}