package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.repositories.RepoCategorias;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class RepoCategoriasMemoria<T> extends RepositorioMemoria<Categoria> implements RepoCategorias {

    public RepoCategoriasMemoria(DAOMemoria<Categoria> dao) {
        super(dao);
    }

    @Override
    public Optional<Categoria> findByNombreCategoria(String categoria) {
        String[] subCategoria = categoria.split("-");
        return this.getDao().buscar(condicionCategoria(subCategoria[0].trim(), subCategoria[1].trim()))
                .stream().findFirst();
    }

    @Override
    public Optional<Categoria> findByActividadAndTipoConsumo(String actividad, String tipoConsumo) {
        return this.getDao().buscar(condicionCategoria(actividad, tipoConsumo))
                .stream().findFirst();
    }

    private Predicate<Categoria> condicionCategoria(String actividad, String tipoConsumo) {
        return categoria -> categoria.getActividad().equals(actividad) &&
                categoria.getTipoConsumo().equals(tipoConsumo);
    }
}
