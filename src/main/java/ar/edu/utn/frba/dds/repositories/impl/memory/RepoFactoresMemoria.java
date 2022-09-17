package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
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

    public List<FactorEmision> findByCategoria(Categoria categoria) {
        return this.getDao().buscar(condicionCategoria(categoria));
    }

    private Predicate<FactorEmision> condicionCategoria(Categoria categoria) {
        return factorEmision -> factorEmision.getCategoria().equals(categoria.toString());
    }
}
