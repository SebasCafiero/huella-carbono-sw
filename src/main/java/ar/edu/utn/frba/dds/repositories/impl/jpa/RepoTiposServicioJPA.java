package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;
import ar.edu.utn.frba.dds.repositories.RepoTiposServicio;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RepoTiposServicioJPA<T> extends RepositorioPersistente<TipoServicio> implements RepoTiposServicio {

    public RepoTiposServicioJPA(DAOHibernate<TipoServicio> dao) {
        super(dao);
    }

    @Override
    public Optional<TipoServicio> findByEquality(String nombre) {
        return this.getDao().buscar(condicionCategoria(nombre)).stream()
                .findFirst();
    }

    private CriteriaQuery<TipoServicio> condicionCategoria(String nombre) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<TipoServicio> factorQuery = criteriaBuilder.createQuery(TipoServicio.class);

        Root<TipoServicio> condicionRaiz = factorQuery.from(TipoServicio.class);

        Predicate condiciontransporte = criteriaBuilder.equal(condicionRaiz.get("nombre"), nombre);

        factorQuery.where(condiciontransporte);

        return factorQuery;
    }


}
