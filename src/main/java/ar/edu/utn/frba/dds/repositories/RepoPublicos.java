package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransportePublico;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;

public interface RepoPublicos extends Repositorio<TransportePublico> {

    TransportePublico findByEquality(TipoTransportePublico tipo, String linea);
}
