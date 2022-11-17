package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.transportes.TipoCombustible;
import ar.edu.utn.frba.dds.entities.transportes.TipoVehiculo;
import ar.edu.utn.frba.dds.entities.transportes.VehiculoParticular;
import ar.edu.utn.frba.dds.repositories.RepoParticulares;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepoParticularesJPA<T> extends RepositorioPersistente<VehiculoParticular> implements RepoParticulares {

    public RepoParticularesJPA(DAOHibernate<VehiculoParticular> dao) {
        super(dao);
    }

    @Override
    public VehiculoParticular findByEquality(TipoVehiculo tipoVehiculo, TipoCombustible tipoCombustible) {
        return this.getDao().buscar(condicionCategoria(tipoVehiculo, tipoCombustible)).stream()
                .findFirst().orElse(null);
    }

    private CriteriaQuery<VehiculoParticular> condicionCategoria(TipoVehiculo tipoVehiculo, TipoCombustible tipoCombustible) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<VehiculoParticular> factorQuery = criteriaBuilder.createQuery(VehiculoParticular.class);

        Root<VehiculoParticular> condicionRaiz = factorQuery.from(VehiculoParticular.class);

        Predicate condicionTipo = criteriaBuilder.equal(condicionRaiz.get("tipoVehiculo"), tipoVehiculo);
        Predicate condicionNro = criteriaBuilder.equal(condicionRaiz.get("tipoCombustible"), tipoCombustible);
        Predicate condiciontransporte = criteriaBuilder.and(condicionTipo, condicionNro);

        factorQuery.where(condiciontransporte);

        return factorQuery;
    }


}
