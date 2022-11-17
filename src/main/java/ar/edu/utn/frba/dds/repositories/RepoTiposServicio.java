package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;

public interface RepoTiposServicio extends Repositorio<TipoServicio> {

    TipoServicio findByEquality(String nombre);
}
