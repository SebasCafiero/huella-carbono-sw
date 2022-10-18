package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.List;
import java.util.Optional;

public class UserUtils {
    private Repositorio<User> repositorio;

    public UserUtils(){
        this.repositorio = FactoryRepositorio.get(User.class);
    }

    public boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        Optional<User> usuario = buscarUsuarioEnRepo(username, password);
        return usuario.isPresent();
    }
    private Optional<User> buscarUsuarioEnRepo(String username, String password) {
        List<User> users = this.repositorio.buscarTodos();
        return users
                .stream()
                .filter(cliente -> cliente.getUsername().equals(username))
                .filter(cliente -> cliente.getPassword().equals(password))
                .findFirst();
    }

    public void agregar( User user){
        this.repositorio.agregar(user);
    }
}
