package ar.edu.utn.frba.dds.repositories.daos;

import javax.persistence.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
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

    //TODO podríamos tener un archivo solamente con las prioridades y dsp las variables de entorno (1º persistenceXML, 2º arch, 3º env vars)
    private static Map<String, Object> seteoPropiedades() throws URISyntaxException {
        //https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        final String motorDB = "jdbc:postgresql://";

        String[] keys = new String[]{
                //, "javax.persistence.jdbc.url",
                //"javax.persistence.jdbc.user", "javax.persistence.jdbc.password",
                "DATABASE_URL",
                "hibernate.show_sql",
                "ddlauto",
                "jdbc.driver"
                //"javax.persistence.schema-generation.database.action"
        };

        for (String key : keys) {
            if (env.containsKey(key)) {

                if (key.equals("DATABASE_URL")) {
                    // https://devcenter.heroku.com/articles/connecting-heroku-postgres#connecting-in-java
                    String value = env.get(key);
                    //postgres://<username>:<password>@<host>:<port>/<dbname>
                    URI dbUri = new URI(value);

                    String username = dbUri.getUserInfo().split(":")[0];
                    String password = dbUri.getUserInfo().split(":")[1];
                    //javax.persistence.jdbc.url=jdbc:postgresql://localhost/dblibros
                    value = motorDB + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();// + "?sslmode=require";
                    configOverrides.put("hibernate.connection.url", value);
                    configOverrides.put("hibernate.connection.username", username);
                    configOverrides.put("hibernate.connection.password", password);
                    //  configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

                }
                if (key.equals("ddlauto")) {
                    String value = env.get(key);
                    configOverrides.put("hibernate.hbm2ddl.auto", value);
                }
//                if (key.equals("jdbc.driver")) {
//                    String value = env.get(key);
////                    configOverrides.put("javax.persistence.jdbc.driver", value);
//                    configOverrides.put("hibernate.connection.driver_class", value);
//                }

            }
        }
        return configOverrides;
    }

}