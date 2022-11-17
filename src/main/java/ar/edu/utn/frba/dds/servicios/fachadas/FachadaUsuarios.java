package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.repositories.RepoUsuarios;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.server.login.User;
import spark.Request;

import java.util.Optional;

public class FachadaUsuarios {
//    private Repositorio<User> repositorio;
    private RepoUsuarios repoUsuarios;

    public FachadaUsuarios() {
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

    public Optional<User> findById(Integer idUsuario) {
        User user = repoUsuarios.buscar(idUsuario);
        return Optional.ofNullable(user);
    }

    public boolean estaLogueado(Request request) {
        return request.session().attribute("idUsuario") != null;
    }

}