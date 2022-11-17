package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepoFactoresJPA<T> extends RepositorioPersistente<FactorEmision> implements RepoFactores {

    public RepoFactoresJPA(DAOHibernate<FactorEmision> dao) {
        super(dao);
    }

    public List<FactorEmision> findByCategoria(Categoria categoria) {
        return this.getDao().buscar(condicionCategoria(categoria));
    }

    private CriteriaQuery<FactorEmision> condicionCategoria(Categoria categoria) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<FactorEmision> factorQuery = criteriaBuilder.createQuery(FactorEmision.class);

        Root<FactorEmision> condicionRaiz = factorQuery.from(FactorEmision.class);

        Predicate condicionCategoria = criteriaBuilder.equal(condicionRaiz.get("categoria"), categoria);

        factorQuery.where(condicionCategoria);

        return factorQuery;
    }
}
