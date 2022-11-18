package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransportePublico;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.repositories.RepoPublicos;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.function.Predicate;

public class RepoPublicosMemoria<T> extends RepositorioMemoria<TransportePublico> implements RepoPublicos {

    public RepoPublicosMemoria(DAOMemoria<TransportePublico> dao) {
        super(dao);
    }

    @Override
    public TransportePublico findByEquality(TipoTransportePublico tipo, String linea) {
        return this.getDao().buscar(condicionTransporte(tipo, linea)).stream().findFirst().orElse(null);
    }

    private Predicate<TransportePublico> condicionTransporte(TipoTransportePublico tipo, String linea) {
        return transporte -> transporte.getTipo().equals(tipo)
                && transporte.getLinea().equals(linea);
    }
}
