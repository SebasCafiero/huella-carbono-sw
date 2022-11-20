package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;

import java.util.Optional;

public interface RepoTiposServicio extends Repositorio<TipoServicio> {

    Optional<TipoServicio> findByEquality(String nombre);
}
