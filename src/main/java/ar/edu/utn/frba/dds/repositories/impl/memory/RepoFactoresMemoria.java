package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class RepoFactoresMemoria<T> extends RepositorioMemoria<FactorEmision> implements RepoFactores {

    public RepoFactoresMemoria(DAOMemoria<FactorEmision> dao) {
        super(dao);
    }

    public List<FactorEmision> findByCategoria(String categoria) {
        return this.getDao().buscar(condicionCategoria(categoria));
    }

    public Optional<FactorEmision> findByCategoriaAndUnidad(String categoria, String unidad) {
        return this.getDao().buscar(condicionCategoriaAndUnidad(categoria, unidad)).stream().findFirst();
    }

    private Predicate<FactorEmision> condicionCategoria(String categoria) {
        return factorEmision -> factorEmision.getCategoria().equals(categoria);
    }

    private Predicate<FactorEmision> condicionCategoriaAndUnidad(String categoria, String unidad) {
        return factorEmision -> factorEmision.getCategoria().equals(categoria)
                && factorEmision.getUnidad().equals(unidad);
    }
}
