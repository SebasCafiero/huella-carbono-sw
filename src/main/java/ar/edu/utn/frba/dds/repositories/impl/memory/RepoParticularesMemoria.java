package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.entities.transportes.TipoCombustible;
import ar.edu.utn.frba.dds.entities.transportes.TipoVehiculo;
import ar.edu.utn.frba.dds.entities.transportes.VehiculoParticular;
import ar.edu.utn.frba.dds.repositories.RepoParticulares;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.function.Predicate;

public class RepoParticularesMemoria<T> extends RepositorioMemoria<VehiculoParticular> implements RepoParticulares {

    public RepoParticularesMemoria(DAOMemoria<VehiculoParticular> dao) {
        super(dao);
    }

    @Override
    public VehiculoParticular findByEquality(TipoVehiculo tipoVehiculo, TipoCombustible tipoCombustible) {
        return this.getDao().buscar(condicionCategoria(tipoVehiculo, tipoCombustible)).stream()
                .findFirst().orElse(null);
    }

    private Predicate<VehiculoParticular> condicionCategoria(TipoVehiculo tipoVehiculo, TipoCombustible tipoCombustible) {
        return transporte -> transporte.getTipo().equals(tipoVehiculo)
                && transporte.getCombustible().equals(tipoCombustible);
    }


}
