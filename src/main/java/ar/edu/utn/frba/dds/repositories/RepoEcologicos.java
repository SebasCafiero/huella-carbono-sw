package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransporteEcologico;
import ar.edu.utn.frba.dds.entities.transportes.TransporteEcologico;

import java.util.Optional;

public interface RepoEcologicos extends Repositorio<TransporteEcologico> {

    Optional<TransporteEcologico> findByEquality(TipoTransporteEcologico tipo);
}
