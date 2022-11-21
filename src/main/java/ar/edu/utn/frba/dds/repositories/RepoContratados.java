package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.ServicioContratado;
import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;

import java.util.Optional;

public interface RepoContratados extends Repositorio<ServicioContratado> {

    Optional<ServicioContratado> findByEquality(TipoServicio tipo);
}
