package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.login.User;
import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;

import java.util.Optional;
import java.util.function.Predicate;

public class RepoUsuariosMemoria<T> extends RepositorioMemoria<User> implements RepoUsuarios {

    public RepoUsuariosMemoria(DAOMemoria<User> dao) {
        super(dao);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.getDao().buscar(condicionDocumento(username)).stream().findFirst();
    }

    private Predicate<User> condicionDocumento(String username) {
        return user -> user.getUsername().equals(username);
    }
}
