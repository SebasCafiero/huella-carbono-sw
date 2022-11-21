package ar.edu.utn.frba.dds.repositories.utils;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Supplier;

public class EntityManagerHelper {

    private static EntityManagerFactory emf;

    private static ThreadLocal<EntityManager> threadLocal;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("db", seteoPropiedades());
            threadLocal = new ThreadLocal<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EntityManager entityManager() {
        return getEntityManager();
    }

    public static EntityManager getEntityManager() {
        EntityManager manager = threadLocal.get();
        if (manager == null || !manager.isOpen()) {
            manager = emf.createEntityManager();
            threadLocal.set(manager);
        }
        return manager;
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        em.close();
    }

    public static void beginTransaction() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if(!tx.isActive()){
            tx.begin();
        }
    }

    public static void commit() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()){
            tx.commit();
        }

    }

    public static void rollback(){
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()){
            tx.rollback();
        }
    }

    public static Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public static void persist(Object o){
        entityManager().persist(o);
    }

    public static void withTransaction(Runnable action) {
        withTransaction(() -> {
            action.run();
            return null;
        });
    }
    public static <A> A withTransaction(Supplier<A> action) {
        beginTransaction();
        try {
            A result = action.get();
            commit();
            return result;
        } catch(Throwable e) {
            rollback();
            throw e;
        }
    }

    private static Map<String, Object> seteoPropiedades() throws URISyntaxException {
        Map<String, String> varEntorno = System.getenv();
        Map<String, String> varArchivo = cargarArchivoConfigurable();
        Map<String, Object> configuraciones = new HashMap<String, Object>();
        final String motorDB = "jdbc:postgresql://";

        String[] keys = new String[]{
                "DATABASE_URL",
                "show_sql",
                "ddlauto",
                "driver"
                //"javax.persistence.schema-generation.database.action"
        };

        for (String key : keys) { //Ver de poner como puse el default en SystemProperties
            if (varEntorno.containsKey(key) || varArchivo.containsKey(key)) {
                String value;
                if(varEntorno.containsKey(key))
                    value = varEntorno.get(key);
                else
                    value = varArchivo.get(key);

                if (key.equals("DATABASE_URL")) {
                    //postgres://<username>:<password>@<host>:<port>/<dbname>
                    URI dbUri = new URI(value);

                    String username = dbUri.getUserInfo().split(":")[0];
                    String password = dbUri.getUserInfo().split(":")[1];
                    //url=jdbc:postgresql://host:port/dbname
                    value = motorDB + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();// + "?sslmode=require";
                    configuraciones.put("hibernate.connection.url", value);
//                    configuraciones.put("javax.persistence.jdbc.url", value);
                    configuraciones.put("hibernate.connection.username", username);
//                    configuraciones.put("javax.persistence.jdbc.user", value);
                    configuraciones.put("hibernate.connection.password", password);
//                    configuraciones.put("javax.persistence.jdbc.password", value);
//                    configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

                }
                if (key.equals("ddlauto")) {
                    configuraciones.put("hibernate.hbm2ddl.auto", value);
//                    configuraciones.put("javax.persistence.schema-generation.database.action", value);
                }
                if (key.equals("show_sql")) {
                    configuraciones.put("hibernate.show_sql", true);
                }
                if (key.equals("driver")) {
//                    configOverrides.put("javax.persistence.jdbc.driver", value);
                    configuraciones.put("hibernate.connection.driver_class", value);
                }

            }
        }
        return configuraciones;
    }

    private static Map<String, String> cargarArchivoConfigurable() {
        Map<String, String> propiedadesMap = new HashMap<>();
        String path = "resources/persistence.properties";
        try {
            Properties propiedades = new Properties();
//            archivo = new File(path);
            FileReader file = new FileReader(path);
            propiedades.load(file);

            List<?> propiedadesList = Collections.list(propiedades.propertyNames());
            System.out.println("persistence.properties: "+propiedadesList);
            propiedadesList.forEach(p -> {
                String prop = p.toString();
                propiedadesMap.put(prop, propiedades.getProperty(prop));
            });

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo " + path);
//            System.out.println(archivo.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return propiedadesMap;
    }

}