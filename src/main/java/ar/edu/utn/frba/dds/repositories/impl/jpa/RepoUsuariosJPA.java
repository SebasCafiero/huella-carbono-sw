package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.login.User;
import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RepoUsuariosJPA<T> extends RepositorioPersistente<User> implements RepoUsuarios {

    public RepoUsuariosJPA(DAOHibernate<User> dao) {
        super(dao);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.getDao().buscar(condicionUsername(username)).stream().findFirst();
    }

    private CriteriaQuery<User> condicionUsername(String username) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<User> categoriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> condicionRaiz = categoriaQuery.from(User.class);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("username"), username);

        categoriaQuery.where(condicion);

        return categoriaQuery;
    }
}
