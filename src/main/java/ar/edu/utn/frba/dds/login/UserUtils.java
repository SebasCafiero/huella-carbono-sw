package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.repositories.impl.jpa.RepoUsuarios;

import java.util.Optional;

public class UserUtils {

    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        Optional<User> usuario = buscarUsuarioEnRepo(username, password);
        return usuario.isPresent();
    }
    private static Optional<User> buscarUsuarioEnRepo(String username, String password) {
        return RepoUsuarios.getRepoUsuariosInstance().obtenerUsuarios()
                .stream()
                .filter(cliente -> cliente.getUsername().equals(username))
                .filter(cliente -> cliente.getPassword().equals(password))
                .findFirst();
    }
}
