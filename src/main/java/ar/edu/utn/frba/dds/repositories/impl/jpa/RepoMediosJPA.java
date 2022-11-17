package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.repositories.RepoMedios;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.Repositorio;

public class RepoMediosJPA<T> extends RepositorioPersistente<MedioDeTransporte> implements RepoMedios {
    private Repositorio<TransporteEcologico> repoEcologicos;
    private Repositorio<TransportePublico> repoPublicos;
    private Repositorio<VehiculoParticular> repoParticulares;
    private Repositorio<ServicioContratado> repoContratados;

    public RepoMediosJPA(DAOHibernate<MedioDeTransporte> dao) {
        super(dao);
    }

    @Override
    public MedioDeTransporte findByEquality(MedioDeTransporte medio) {
        return null;
    }

    @Override
    public MedioDeTransporte findByEquality(String tipo, String primerDiscriminante, String segundoDiscriminante) {
        return null;
    }
}
