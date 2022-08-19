package ar.edu.utn.frba.dds.repositories.factories;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;
import ar.edu.utn.frba.dds.repositories.daos.DAOJPA;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.testMemoData.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class FactoryRepositorio {
    private static HashMap<String, Repositorio> repos;

    static {
        repos = new HashMap<>();
    }

    public static <T extends EntidadPersistente> Repositorio<T> get(Class<T> type){
        Repositorio<T> repo;
        if(repos.containsKey(type.getName())){
            repo = (Repositorio<T>) repos.get(type.getName());
        }
        else{
            if(!isJPA()) {
                if(type.equals(Organizacion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(Data.getDataOrganizacion()));
                } else if(type.equals(Medicion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(Data.getDataMedicion()));
                } else if(type.equals(FactorEmision.class)) {
                    repo = (Repositorio<T>) new RepoFactoresMemoria(new DAOMemoria<>(new ArrayList<>()));
                } else {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(new ArrayList<>()));
                }
            } else {
                repo = new RepositorioPersistente<>(new DAOJPA<>(new ArrayList<>()));
            }

            // Esta linea es la posta ==> repo = new Repositorio<>(new DAOMemoria<>(Data.getData(type)));
            repos.put(type.getName(), repo);
        }
        return repo;
    }

    private static boolean isJPA() {
        return false;
    }
}
