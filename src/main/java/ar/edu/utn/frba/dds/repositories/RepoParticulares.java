package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.transportes.TipoCombustible;
import ar.edu.utn.frba.dds.entities.transportes.TipoVehiculo;
import ar.edu.utn.frba.dds.entities.transportes.VehiculoParticular;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

public interface RepoParticulares extends Repositorio<VehiculoParticular> {

    VehiculoParticular findByEquality(TipoVehiculo tipoVehiculo, TipoCombustible tipoCombustible);

}