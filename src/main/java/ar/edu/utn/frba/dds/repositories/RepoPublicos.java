package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransportePublico;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;

import java.util.Optional;

public interface RepoPublicos extends Repositorio<TransportePublico> {

    Optional<TransportePublico> findByEquality(TipoTransportePublico tipo, String linea);
}
