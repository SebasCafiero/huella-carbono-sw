package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransportePublico;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.repositories.RepoPublicos;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RepoPublicosJPA<T> extends RepositorioPersistente<TransportePublico> implements RepoPublicos {

    public RepoPublicosJPA(DAOHibernate<TransportePublico> dao) {
        super(dao);
    }

    @Override
    public Optional<TransportePublico> findByEquality(TipoTransportePublico tipo, String linea) {
        return this.getDao().buscar(condicionTransporte(tipo, linea)).stream().findFirst();
    }

    private CriteriaQuery<TransportePublico> condicionTransporte(TipoTransportePublico tipo, String linea) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<TransportePublico> categoriaQuery = criteriaBuilder.createQuery(TransportePublico.class);

        Root<TransportePublico> condicionRaiz = categoriaQuery.from(TransportePublico.class);

        Predicate condicionTipo = criteriaBuilder.equal(condicionRaiz.get("tipo"), tipo);
        Predicate condicionNro = criteriaBuilder.equal(condicionRaiz.get("linea"), linea);
        Predicate condicionTransporte = criteriaBuilder.and(condicionTipo, condicionNro);

        categoriaQuery.where(condicionTransporte);

        return categoriaQuery;
    }
}
