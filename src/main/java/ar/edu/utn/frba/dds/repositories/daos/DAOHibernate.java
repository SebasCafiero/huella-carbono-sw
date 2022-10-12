package ar.edu.utn.frba.dds.repositories.daos;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type){
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
    public T buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(type, id);
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
    public void modificar(T unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void eliminar(T unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
