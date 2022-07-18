package ar.edu.utn.frba.dds.repositories.factories;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.testMemoData.Data;

import java.util.HashMap;

public class FactoryRepositorio {
    private static HashMap<String, Repositorio> repos;

    static {
        repos = new HashMap<>();
    }

    public static <T> Repositorio<T> get(Class<T> type){
        Repositorio<T> repo;
        if(repos.containsKey(type.getName())){
            repo = repos.get(type.getName());
        }
        else{
            if(type.equals(Organizacion.class)){
                repo = new Repositorio<>(new DAOMemoria<>(Data.getDataOrganizacion()));
            } else {
                repo = new Repositorio<>(new DAOMemoria<>(Data.getDataMedicion()));
            }
            // Esta linea es la posta ==> repo = new Repositorio<>(new DAOMemoria<>(Data.getData(type)));
            repos.put(type.toString(), repo);
        }
        return repo;
    }
}
