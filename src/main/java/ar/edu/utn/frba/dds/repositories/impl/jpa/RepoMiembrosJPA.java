package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RepoMiembrosJPA<T> extends RepositorioPersistente<Miembro> implements RepoMiembros {

    public RepoMiembrosJPA(DAOHibernate<Miembro> dao) {
        super(dao);
    }

    @Override
    public Optional<Miembro> findByDocumento(TipoDeDocumento tipoDeDocumento, Integer nroDocumento) {
        return this.getDao().buscar(condicionDocumento(tipoDeDocumento, nroDocumento)).stream().findFirst();
    }

    private CriteriaQuery<Miembro> condicionDocumento(TipoDeDocumento tipoDeDocumento, Integer nroDocumento) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Miembro> categoriaQuery = criteriaBuilder.createQuery(Miembro.class);

        Root<Miembro> condicionRaiz = categoriaQuery.from(Miembro.class);

        Predicate condicionTipo = criteriaBuilder.equal(condicionRaiz.get("tipoDeDocumento"), tipoDeDocumento);
        Predicate condicionNro = criteriaBuilder.equal(condicionRaiz.get("nroDocumento"), nroDocumento);
        Predicate condicionMiembro = criteriaBuilder.and(condicionTipo, condicionNro);

        categoriaQuery.where(condicionMiembro);

        return categoriaQuery;
    }
}
