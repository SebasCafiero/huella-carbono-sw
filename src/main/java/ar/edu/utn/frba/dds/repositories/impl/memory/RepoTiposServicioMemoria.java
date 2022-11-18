package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;
import ar.edu.utn.frba.dds.repositories.RepoTiposServicio;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.function.Predicate;


public class RepoTiposServicioMemoria<T> extends RepositorioMemoria<TipoServicio> implements RepoTiposServicio {

    public RepoTiposServicioMemoria(DAOMemoria<TipoServicio> dao) {
        super(dao);
    }

    @Override
    public TipoServicio findByEquality(String nombre) {
        return this.getDao().buscar(condicionCategoria(nombre)).stream()
                .findFirst().orElse(null);
    }

    private Predicate<TipoServicio> condicionCategoria(String nombre) {
        return tipoTransporte -> tipoTransporte.getNombre().equals(nombre);
    }


}
