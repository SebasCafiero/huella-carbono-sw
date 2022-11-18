package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

public interface RepoOrganizaciones extends Repositorio<Organizacion> {

    Organizacion findByRazonSocial(String razonSocial);

}
