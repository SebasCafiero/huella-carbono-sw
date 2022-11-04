package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioMemoria;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class RepoMiembrosMemoria<T> extends RepositorioMemoria<Miembro> implements RepoMiembros {

    public RepoMiembrosMemoria(DAOMemoria<Miembro> dao) {
        super(dao);
    }

    @Override
    public Optional<Miembro> findByDocumento(TipoDeDocumento tipoDeDocumento, Integer nroDocumento) {
        return this.getDao().buscar(condicionDocumento(tipoDeDocumento, nroDocumento)).stream().findFirst();
    }

    @Override
    public Optional<Miembro> findByUser(Integer id) {
        return Optional.empty();
    }

    private Predicate<Miembro> condicionDocumento(TipoDeDocumento tipoDeDocumento, Integer nroDocumento) {
        return miembro -> miembro.getTipoDeDocumento() != null && miembro.getNroDocumento() != null &&
                Objects.equals(miembro.getTipoDeDocumento(), tipoDeDocumento) &&
                Objects.equals(miembro.getNroDocumento(), nroDocumento);
    }
}
