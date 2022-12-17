package ar.edu.utn.frba.dds.repositories.daos;

import ar.edu.utn.frba.dds.repositories.utils.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {
        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> critera = builder.createQuery(this.type);
        critera.from(type);
        List<T> entities = EntityManagerHelper.getEntityManager().createQuery(critera).getResultList();
        return entities;
    }

    @Override
    public Optional<T> buscar(Integer id) {
        return id == null ? Optional.empty() : Optional.ofNullable(EntityManagerHelper.getEntityManager().find(type, id));
    }

    public List<T> buscar(CriteriaQuery<T> condicion) {
        return EntityManagerHelper.getEntityManager()
                .createQuery(condicion)
                .getResultList();
    }

    @Override
    public T agregar(T unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        return unObjeto;
    }

    @Override
    public T modificar(T unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        T merged = EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        return merged;
    }

    @Override
    public void eliminar(T unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public Optional<T> getReferenceById(Integer id) {
        return id == null ? Optional.empty() : Optional.ofNullable(EntityManagerHelper.getEntityManager().getReference(type, id));
    }

    public void sync(T entity) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().refresh(entity);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
