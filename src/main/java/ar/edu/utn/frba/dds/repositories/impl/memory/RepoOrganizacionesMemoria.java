package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.repositories.RepoOrganizaciones;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class RepoOrganizacionesMemoria<T> extends RepositorioMemoria<Organizacion> implements RepoOrganizaciones {

    public RepoOrganizacionesMemoria(DAOMemoria<Organizacion> dao) {
        super(dao);
    }

    @Override
    public Optional<Organizacion> findByRazonSocial(String razonSocial) {
        return this.getDao().buscar(condicionRazonSocial(razonSocial)).stream().findFirst();
    }

    private Predicate<Organizacion> condicionRazonSocial(String razonSocial) {
        return organizacion -> Objects.equals(organizacion.getRazonSocial(), razonSocial);
    }
}
