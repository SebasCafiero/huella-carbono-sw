package ar.edu.utn.frba.dds.server;

import spark.Spark;
import spark.debug.DebugScreen;

import static spark.Spark.port;

public class Server {
    public static void main(String[] args) {
        //port(9000);
        port(getHerokuAssignedPort());
        Router.init();
        DebugScreen.enableDebugScreen();


        }

        static int getHerokuAssignedPort() {
            System.out.println(System.getenv("nombreVariable"));
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (processBuilder.environment().get("PORT") != null) {
                return Integer.parseInt(processBuilder.environment().get("PORT"));
            }
            return 9000; //return default port if heroku-port isn't set (i.e. on localhost)
        }

}