package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.server.login.User;

import java.util.Optional;

public interface RepoUsuarios extends Repositorio<User> {
    Optional<User> findByUsername(String username);
}
