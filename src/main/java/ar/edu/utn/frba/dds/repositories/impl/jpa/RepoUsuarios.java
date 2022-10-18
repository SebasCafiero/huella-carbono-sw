package ar.edu.utn.frba.dds.repositories.impl.jpa;


import ar.edu.utn.frba.dds.login.User;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepoUsuarios implements WithGlobalEntityManager {

    private static RepoUsuarios INSTANCE = new RepoUsuarios();

    private RepoUsuarios() {
    }

    public static RepoUsuarios getRepoUsuariosInstance() {
        return INSTANCE;
    }

    public List<User> obtenerUsuarios() {
        return entityManager().createQuery("from User ", User.class)
                .getResultList();
    }

    public User obtenerUsuario(long id) {
        return entityManager().find(User.class, id);
    }


    public User getById(long id) {
        return entityManager().find(User.class, id);
    }

    public void agregarUsuario(User usuario) {
        entityManager().merge(usuario);
    }

    public Boolean existeUsuario(String nombre) {
        return obtenerUsuarios().stream().anyMatch(usuario -> nombre.equals(usuario.getUsername()));
    }

}