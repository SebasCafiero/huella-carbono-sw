package ar.edu.utn.frba.dds.repositories.factories;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoCategoriasJPA;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoFactoresJPA;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoCategoriasMemoria;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.testMemoData.Data;

import javax.persistence.Entity;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class FactoryRepositorio {
    private static Boolean jpa;
    private static HashMap<String, Repositorio> repos;

    static {
        repos = new HashMap<>();
    }

    public static <T> Repositorio<T> get(Class<T> type) {
        return get(type, isJPA());
    }

    public static <T> Repositorio<T> get(Class<T> type, boolean isJPA){
        Repositorio<T> repo;
        if(repos.containsKey(type.getName())){
            repo = (Repositorio<T>) repos.get(type.getName());
        }
        else{
            if(!isJPA) {
                if(type.equals(Organizacion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataOrganizacion()));
                } else if(type.equals(Medicion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataMedicion()));
                } else if(type.equals(BatchMedicion.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataBatchMedicion()));
                } else if(type.equals(Miembro.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, Data.getDataMiembro()));
                } else if(type.equals(FactorEmision.class)) {
                    repo = new RepoFactoresMemoria(new DAOMemoria<>(FactorEmision.class, Data.getDataFactorEmision()));
                } else if(type.equals(Categoria.class)) {
                    repo = new RepoCategoriasMemoria(new DAOMemoria<>(Categoria.class, Data.getDataCategorias()));
                } else if(type.equals(AgenteSectorial.class)) {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type,Data.getDataAgenteSectorial()));
                } else {
                    repo = new RepositorioMemoria<>(new DAOMemoria<>(type, new ArrayList<>()));
                }
            } else {
                if(type.isAnnotationPresent(Entity.class)) {
                    if (type.equals(FactorEmision.class)) {
                        repo = new RepoFactoresJPA(new DAOHibernate<>(type));
                    } else if (type.equals(Categoria.class)) {
                        repo = new RepoCategoriasJPA(new DAOHibernate<>(type));
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

    private static boolean isJPA() {
        if(jpa != null)
            return jpa;

        try {
            Properties propiedades = new Properties();
            FileReader file = new FileReader("resources/aplication.properties");
            propiedades.load(file);
            jpa = propiedades.getProperty("jpa").equals("true");
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("El archivo properties no existe");
        }
        return jpa;
    }
}
