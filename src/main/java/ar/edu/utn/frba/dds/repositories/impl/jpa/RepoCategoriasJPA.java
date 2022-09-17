package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.repositories.RepoCategorias;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.RepositorioPersistente;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RepoCategoriasJPA<T> extends RepositorioPersistente<Categoria> implements RepoCategorias {

    public RepoCategoriasJPA(DAOHibernate<Categoria> dao) {
        super(dao);
    }

    @Override
    public Optional<Categoria> findByActividadAndTipoConsumo(String actividad, String tipoConsumo) {
        return this.getDao().buscar(condicionCategoria(actividad, tipoConsumo)).stream().findFirst();
    }

    @Override
    public Optional<Categoria> findByNombreCategoria(String categoria) {
        String[] subCategoria = categoria.split("-");
        return this.getDao().buscar(condicionCategoria(subCategoria[0].trim(), subCategoria[1].trim()))
                .stream().findFirst();
    }

    private CriteriaQuery<Categoria> condicionCategoria(String actividad, String tipoConsumo) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Categoria> categoriaQuery = criteriaBuilder.createQuery(Categoria.class);

        Root<Categoria> condicionRaiz = categoriaQuery.from(Categoria.class);

        Predicate condicionActividad = criteriaBuilder.equal(condicionRaiz.get("actividad"), actividad);
        Predicate condicionTipoConsumo = criteriaBuilder.equal(condicionRaiz.get("tipoConsumo"), tipoConsumo);
        Predicate condicionCategoria = criteriaBuilder.and(condicionActividad, condicionTipoConsumo);

        categoriaQuery.where(condicionCategoria);

        return categoriaQuery;
    }
}
