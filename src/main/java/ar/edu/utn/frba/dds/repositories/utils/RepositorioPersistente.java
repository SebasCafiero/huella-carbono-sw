package ar.edu.utn.frba.dds.repositories.utils;

import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.daos.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class RepositorioPersistente<T> implements Repositorio<T> {
    private final DAOHibernate<T> dao;

    public DAOHibernate<T> getDao() {
        return dao;
    }

    public RepositorioPersistente(DAOHibernate<T> dao) {
        this.dao = dao;
    }

    @Override
    public T agregar(T unObjeto) {
        return this.dao.agregar(unObjeto);
    }

    @Override
    public void modificar(T unObjeto) {
        this.dao.modificar(unObjeto);
    }

    @Override
    public void eliminar(T unObjeto) {
        this.dao.eliminar(unObjeto);
    }

    @Override
    public List<T> buscarTodos() {
        return this.dao.buscarTodos();
    }

    @Override
    public T buscar(int id) {
        return this.dao.buscar(id);
    }

    @Override
    public void modificar(int id, T unObjeto) {
        this.dao.modificar(unObjeto);
    }

    public CriteriaBuilder criteriaBuilder(){
        return EntityManagerHelper.getEntityManager().getCriteriaBuilder();
    }
}
