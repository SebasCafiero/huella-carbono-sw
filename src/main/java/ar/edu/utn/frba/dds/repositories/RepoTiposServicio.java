package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

public interface RepoTiposServicio extends Repositorio<TipoServicio> {

    TipoServicio findByEquality(String nombre);
}
