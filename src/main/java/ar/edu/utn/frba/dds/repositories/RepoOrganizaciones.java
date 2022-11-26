package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

import java.util.Optional;

public interface RepoOrganizaciones extends Repositorio<Organizacion> {

    Optional<Organizacion> findByRazonSocial(String razonSocial);

}
