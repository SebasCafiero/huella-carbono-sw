package ar.edu.utn.frba.dds.server;

import spark.Spark;
import spark.debug.DebugScreen;

import static spark.Spark.port;

public class Server {
    public static void main(String[] args) {
        System.out.println("HEROKU-PORT: "+getHerokuAssignedPort());
        port(getHerokuAssignedPort());
        Router.init();
        DebugScreen.enableDebugScreen();
        }

        static int getHerokuAssignedPort() {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String herokuPort = processBuilder.environment().get("PORT"); //System.getenv("PORT");
            if ( herokuPort != null) {
                return Integer.parseInt(herokuPort);
            }
            return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
        }

}