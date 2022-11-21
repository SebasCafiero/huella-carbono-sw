package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.transportes.ServicioContratado;
import ar.edu.utn.frba.dds.entities.transportes.TipoServicio;
import ar.edu.utn.frba.dds.repositories.RepoContratados;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.Optional;
import java.util.function.Predicate;

public class RepoContratadosMemoria<T> extends RepositorioMemoria<ServicioContratado> implements RepoContratados {

    public RepoContratadosMemoria(DAOMemoria<ServicioContratado> dao) {
        super(dao);
    }

    @Override
    public Optional<ServicioContratado> findByEquality(TipoServicio tipo) {
        return this.getDao().buscar(condicionCategoria(tipo)).stream()
                .findFirst();
    }

    private Predicate<ServicioContratado> condicionCategoria(TipoServicio tipo) {
        return transporte -> transporte.getTipo().equals(tipo);
    }

}
