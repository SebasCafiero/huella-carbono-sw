package ar.edu.utn.frba.dds.server.login;

import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import spark.Request;

import java.util.Optional;

public class UserUtils {
//    private Repositorio<User> repositorio;
    private RepoUsuarios repoUsuarios;

    public UserUtils() {
//        this.repositorio = FactoryRepositorio.get(User.class);
        this.repoUsuarios = (RepoUsuarios) FactoryRepositorio.get(User.class);
    }

    public boolean isAuthenticated(String username, String password) {
        return username.isEmpty() || password.isEmpty() || !buscarUsuarioEnRepo(username, password).isPresent();
    }

    public Optional<User> buscarUsuarioEnRepo(String username, String password) {
        Optional<User> mayBeUser = this.repoUsuarios.findByUsername(username);

        if(mayBeUser.isPresent() && mayBeUser.get().getPassword().equals(password))
            return mayBeUser;

        return Optional.empty();
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