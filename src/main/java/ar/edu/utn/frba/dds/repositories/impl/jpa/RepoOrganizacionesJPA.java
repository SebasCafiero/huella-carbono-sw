package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.repositories.RepoOrganizaciones;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepoOrganizacionesJPA<T> extends RepositorioPersistente<Organizacion> implements RepoOrganizaciones {

    public RepoOrganizacionesJPA(DAOHibernate<Organizacion> dao) {
        super(dao);
    }

    @Override
    public Organizacion findByRazonSocial(String razonSocial) {
        return this.getDao().buscar(condicionRazonSocial(razonSocial)).stream().findFirst().orElse(null);
    }

    private CriteriaQuery<Organizacion> condicionRazonSocial(String razonSocial) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Organizacion> factorQuery = criteriaBuilder.createQuery(Organizacion.class);

        Root<Organizacion> condicionRaiz = factorQuery.from(Organizacion.class);

        Predicate condicionCategoria = criteriaBuilder.equal(condicionRaiz.get("razonSocial"), razonSocial);

        factorQuery.where(condicionCategoria);

        return factorQuery;
    }
}
