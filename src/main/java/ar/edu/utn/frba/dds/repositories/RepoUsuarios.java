package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.login.User;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.Optional;

public interface RepoUsuarios extends Repositorio<User> {
    Optional<User> findByUsername(String username);
}
