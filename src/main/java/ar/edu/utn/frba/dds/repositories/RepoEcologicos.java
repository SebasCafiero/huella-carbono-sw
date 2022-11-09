package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoTransporteEcologico;
import ar.edu.utn.frba.dds.entities.transportes.TransporteEcologico;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

public interface RepoEcologicos extends Repositorio<TransporteEcologico> {

    TransporteEcologico findByEquality(TipoTransporteEcologico tipo);
}
