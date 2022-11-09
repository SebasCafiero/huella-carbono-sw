package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.repositories.RepoEcologicos;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepoEcologicosJPA<T> extends RepositorioPersistente<TransporteEcologico> implements RepoEcologicos {

    public RepoEcologicosJPA(DAOHibernate<TransporteEcologico> dao) {
        super(dao);
    }

    @Override
    public TransporteEcologico findByEquality(TipoTransporteEcologico tipo) {
        return this.getDao().buscar(condicionCategoria(tipo)).stream()
                .findFirst().orElse(null);
    }

    private CriteriaQuery<TransporteEcologico> condicionCategoria(TipoTransporteEcologico tipo) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<TransporteEcologico> factorQuery = criteriaBuilder.createQuery(TransporteEcologico.class);

        Root<TransporteEcologico> condicionRaiz = factorQuery.from(TransporteEcologico.class);

        Predicate condiciontransporte = criteriaBuilder.equal(condicionRaiz.get("tipo"), tipo);

        factorQuery.where(condiciontransporte);

        return factorQuery;
    }


}
