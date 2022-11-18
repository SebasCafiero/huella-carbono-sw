package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.ServicioContratado;
import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;

public interface RepoContratados extends Repositorio<ServicioContratado> {

    ServicioContratado findByEquality(TipoServicio tipo);
}
