package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

public interface RepoOrganizaciones {

    Organizacion findByRazonSocial(String razonSocial);

}
