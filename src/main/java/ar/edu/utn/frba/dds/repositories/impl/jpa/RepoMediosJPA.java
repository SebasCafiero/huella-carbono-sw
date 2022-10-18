package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.TransporteEcologico;
import ar.edu.utn.frba.dds.repositories.RepoMedios;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RepoMediosJPA<T> extends RepositorioPersistente<MedioDeTransporte> implements RepoMedios {

    public RepoMediosJPA(DAOHibernate<MedioDeTransporte> dao) {
        super(dao);
    }

    @Override
    public MedioDeTransporte findByEquality(MedioDeTransporte medio) {
        return null;
    }

//    private CriteriaQuery<MedioDeTransporte> condicionIgualdad(MedioDeTransporte medio) {
//        CriteriaBuilder criteriaBuilder = criteriaBuilder();
//        CriteriaQuery<MedioDeTransporte> categoriaQuery = criteriaBuilder.createQuery(MedioDeTransporte.class);
//
//        Root<MedioDeTransporte> condicionRaiz = categoriaQuery.from(MedioDeTransporte.class);
//
//        if(medio instanceof TransporteEcologico)
//
//        Predicate condicionTipo = criteriaBuilder.equal(condicionRaiz.get("tipoDeDocumento"), tipoDeDocumento);
//        Predicate condicionNro = criteriaBuilder.equal(condicionRaiz.get("nroDocumento"), nroDocumento);
//        Predicate condicionMiembro = criteriaBuilder.and(condicionTipo, condicionNro);
//
//        categoriaQuery.where(condicionMiembro);
//
//        return categoriaQuery;
//    }
}
