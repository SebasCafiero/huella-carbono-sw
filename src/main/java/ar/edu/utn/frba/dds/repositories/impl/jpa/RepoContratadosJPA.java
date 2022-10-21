package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.transportes.ServicioContratado;
import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;
import ar.edu.utn.frba.dds.repositories.RepoContratados;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepoContratadosJPA<T> extends RepositorioPersistente<ServicioContratado> implements RepoContratados {

    public RepoContratadosJPA(DAOHibernate<ServicioContratado> dao) {
        super(dao);
    }

    @Override
    public ServicioContratado findByEquality(TipoServicio tipo) {
        return this.getDao().buscar(condicionCategoria(tipo)).stream()
                .findFirst().orElse(null);
    }

    private CriteriaQuery<ServicioContratado> condicionCategoria(TipoServicio tipo) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<ServicioContratado> factorQuery = criteriaBuilder.createQuery(ServicioContratado.class);

        Root<ServicioContratado> condicionRaiz = factorQuery.from(ServicioContratado.class);

        Predicate condiciontransporte = criteriaBuilder.equal(condicionRaiz.get("tipo"), tipo);

        factorQuery.where(condiciontransporte);

        return factorQuery;
    }

}
