package ar.edu.utn.frba.dds.repositories.factories;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoMiembrosJPA;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoFactoresJPA;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoOrganizacionesJPA;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoMiembrosMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoOrganizacionesMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoTrayectosMemoria;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.testMemoData.Data;
import ar.edu.utn.frba.dds.server.SystemProperties;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class FactoryRepositorio {
    private static HashMap<String, Repositorio> repos;


    static {
        repos = new HashMap<>();
    }

    public static <T> Repositorio<T> get(Class<T> type) {
        return get(type, SystemProperties.isJpa());
    }

    public static <T> Repositorio<T> get(Class<T> type, boolean isJPA) {
        Repositorio<T> repo;
        if(repos.containsKey(type.getName())){
            repo = (Repositorio<T>) repos.get(type.getName());
        }
        else{
            if(!isJPA) {
                if(type.equals(Organizacion.class)) {
                    repo = new RepoOrganizacionesMemoria(new DAOMemoria<>(Organizacion.class, Data.getDataOrganizacion()));
                } else if(type.equals(Medicion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataMedicion()));
                } else if(type.equals(BatchMedicion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataBatchMedicion()));
                } else if(type.equals(Miembro.class)) {
                    repo = new RepoMiembrosMemoria(new DAOMemoria<>(Miembro.class, Data.getDataMiembro()));
                } else if(type.equals(FactorEmision.class)) {
                    repo = new RepoFactoresMemoria(new DAOMemoria<>(FactorEmision.class, Data.getDataFactorEmision()));
                } else if(type.equals(Categoria.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<T>(type, (List<T>) Data.getDataCategorias()));
                } else if(type.equals(AgenteSectorial.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataAgenteSectorial()));
                } else if(type.equals(Trayecto.class)) {
                    repo = new RepoTrayectosMemoria(new DAOMemoria<>(Trayecto.class, Data.getDataTrayecto()));
                } else {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type));
                }
            } else {
                if(type.isAnnotationPresent(Entity.class)) {
                    if(type.equals(Organizacion.class)) {
                        repo = new RepoOrganizacionesJPA(new DAOHibernate<>(type));
                    } else if (type.equals(FactorEmision.class)) {
                        repo = new RepoFactoresJPA(new DAOHibernate<>(type));
                    } else if (type.equals(Miembro.class)) {
                        repo = new RepoMiembrosJPA(new DAOHibernate<>(type));
                    } else {
                        repo = new RepositorioPersistente<>(new DAOHibernate<>(type));
                    }
                }
                else {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, new ArrayList<>()));
                }
            }

            repos.put(type.getName(), repo);
        }

        return repo;
    }
}
