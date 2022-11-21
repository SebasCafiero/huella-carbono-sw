package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.server.login.User;
import spark.Request;

import java.util.Optional;

public class FachadaUsuarios {
    private final RepoUsuarios repoUsuarios;

    public FachadaUsuarios() {
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

    public Optional<User> findById(Integer idUsuario) {
        return repoUsuarios.buscar(idUsuario);
    }

    public Optional<User> findByRolId(String rol, Integer idUsuario) {
        return repoUsuarios.findByRolId(rol, idUsuario);
    }

    public boolean estaLogueado(Request request) {
        return request.session().attribute("idUsuario") != null;
    }

}