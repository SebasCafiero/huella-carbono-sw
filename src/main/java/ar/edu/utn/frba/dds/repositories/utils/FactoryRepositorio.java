package ar.edu.utn.frba.dds.repositories.utils;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.medibles.BatchMedicion;
import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;
import ar.edu.utn.frba.dds.server.login.User;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.impl.jpa.*;
import ar.edu.utn.frba.dds.repositories.impl.memory.*;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepositorioMemoria;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepositorioPersistente;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.dataInicial.memory.Data;
import ar.edu.utn.frba.dds.server.SystemProperties;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                } else if(type.equals(TransporteEcologico.class)) {
                    repo = new RepoEcologicosMemoria(new DAOMemoria<>(TransporteEcologico.class));
                } else if(type.equals(TransportePublico.class)) {
                    repo = new RepoPublicosMemoria(new DAOMemoria<>(TransportePublico.class));
                } else if(type.equals(ServicioContratado.class)) {
                    repo = new RepoContratadosMemoria(new DAOMemoria<>(ServicioContratado.class));
                } else if(type.equals(VehiculoParticular.class)) {
                    repo = new RepoParticularesMemoria(new DAOMemoria<>(VehiculoParticular.class));
                } else if(type.equals(TipoServicio.class)) {
                    repo = new RepoTiposServicioMemoria(new DAOMemoria<>(TipoServicio.class));
                } else if(type.equals(User.class)) {
                    repo = new RepoUsuariosMemoria(new DAOMemoria<>(User.class));
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
                    } else if(type.equals(TransporteEcologico.class)) {
                        repo = new RepoEcologicosJPA(new DAOHibernate<>(TransporteEcologico.class));
                    } else if(type.equals(TransportePublico.class)) {
                        repo = new RepoPublicosJPA(new DAOHibernate<>(TransportePublico.class));
                    } else if(type.equals(ServicioContratado.class)) {
                        repo = new RepoContratadosJPA(new DAOHibernate<>(ServicioContratado.class));
                    } else if(type.equals(VehiculoParticular.class)) {
                        repo = new RepoParticularesJPA(new DAOHibernate<>(VehiculoParticular.class));
                    } else if(type.equals(TipoServicio.class)) {
                        repo = new RepoTiposServicioJPA(new DAOHibernate<>(TipoServicio.class));
                    } else if(type.equals(User.class)) {
                        repo = new RepoUsuariosJPA(new DAOHibernate<>(User.class));
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
