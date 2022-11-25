package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.repositories.cache.CacheLocalidad;
import ar.edu.utn.frba.dds.entities.lugares.Municipio;
import ar.edu.utn.frba.dds.repositories.daos.Cache;
import ar.edu.utn.frba.dds.repositories.utils.FactoryCache;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.AdaptadorServicioDDSTPA;

import static spark.Spark.port;

public class Server {
    public static void main(String[] args) {
        System.out.println("HEROKU-PORT: "+getHerokuAssignedPort());
        port(getHerokuAssignedPort());
        loadCache();

        Router.init();
//        DebugScreen.enableDebugScreen();
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String herokuPort = processBuilder.environment().get("PORT"); //System.getenv("PORT");
        if ( herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    static void loadCache() {
        if(SystemProperties.isCalculadoraDistanciasCacheEnabled()) {
            System.out.println("Inicializando datos de api distancias por cache");
            Cache<CacheLocalidad> cacheLocalidades = FactoryCache.get(CacheLocalidad.class);

            AdaptadorServicioDDSTPA adapter = new AdaptadorServicioDDSTPA();

            FactoryRepositorio.get(Municipio.class).buscarTodos().forEach(muni -> {
                adapter.obtenerLocalidades(muni.getIdApiDistancias())
                        .forEach(loca -> cacheLocalidades.put(loca.getNombre(),
                                new CacheLocalidad(muni.getProvincia().getIdApiDistancias(), muni.getId(), loca.getId())));
            });
        } else {
            System.out.println("Cache api distancias deshabilitada.");
        }

    }

}