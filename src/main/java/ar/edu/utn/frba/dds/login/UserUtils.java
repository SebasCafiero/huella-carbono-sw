package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import spark.Request;

import java.util.List;
import java.util.Optional;

public class UserUtils {
//    private Repositorio<User> repositorio;
    private RepoUsuarios repoUsuarios;

    public UserUtils() {
//        this.repositorio = FactoryRepositorio.get(User.class);
        this.repoUsuarios = (RepoUsuarios) FactoryRepositorio.get(User.class);
    }

    public boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        Optional<User> usuario = buscarUsuarioEnRepo(username);
        return usuario.isPresent();
    }
    public Optional<User> buscarUsuarioEnRepo(String username) {
//        List<User> users = this.repositorio.buscarTodos();
//        return users
//                .stream()
//                .filter(cliente -> cliente.getUsername().equals(username))
//                .filter(cliente -> cliente.getPassword().equals(password))
//                .findFirst();
        return this.repoUsuarios.findByUsername(username);
    }

    public void agregar(User user){
        this.repoUsuarios.agregar(user);
    }

    public Optional<User> getUsuarioLogueado(Integer idUsuario) {
        return Optional.ofNullable(repoUsuarios.buscar(idUsuario));
    }

    public boolean estaLogueado(Request request) {
        return request.session().attribute("idUsuario") != null;
    }

}