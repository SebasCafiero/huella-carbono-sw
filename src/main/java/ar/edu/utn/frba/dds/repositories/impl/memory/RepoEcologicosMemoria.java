package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransporteEcologico;
import ar.edu.utn.frba.dds.entities.transportes.TransporteEcologico;
import ar.edu.utn.frba.dds.repositories.RepoEcologicos;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.function.Predicate;

public class RepoEcologicosMemoria<T> extends RepositorioMemoria<TransporteEcologico> implements RepoEcologicos {

    public RepoEcologicosMemoria(DAOMemoria<TransporteEcologico> dao) {
        super(dao);
    }

    @Override
    public TransporteEcologico findByEquality(TipoTransporteEcologico tipo) {
        return this.getDao().buscar(condicionCategoria(tipo)).stream()
                .findFirst().orElse(null);
    }

    private Predicate<TransporteEcologico> condicionCategoria(TipoTransporteEcologico tipo) {
        return transporte -> transporte.getTipo().equals(tipo);
    }
}
