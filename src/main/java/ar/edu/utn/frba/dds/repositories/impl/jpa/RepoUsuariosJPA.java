package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.server.login.User;
import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;

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

    @Override
    public Optional<User> findByRolId(String rol, Integer idRol) {
        return this.getDao().buscar(condicionRol(idRol, rol)).stream().findFirst();
    }

    private CriteriaQuery<User> condicionUsername(String username) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<User> categoriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> condicionRaiz = categoriaQuery.from(User.class);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("username"), username);

        categoriaQuery.where(condicion);

        return categoriaQuery;
    }

    private CriteriaQuery<User> condicionRol(Integer idRol, String rol) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<User> categoriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> condicionRaiz = categoriaQuery.from(User.class);

        Object userFK;
        if (rol.equals("organizacion")) {
            Organizacion entity = new Organizacion();
            entity.setId(idRol);
            userFK = entity;
        } else if(rol.equals("miembro")) {
            Miembro entity = new Miembro();
            entity.setId(idRol);
            userFK = entity;
        } else {
            AgenteSectorial entity = new AgenteSectorial();
            entity.setId(idRol);
            userFK = entity;
        }

        Predicate condicionRol = criteriaBuilder.equal(condicionRaiz.get(rol), userFK);

        categoriaQuery.where(condicionRol);

        return categoriaQuery;
    }
}
