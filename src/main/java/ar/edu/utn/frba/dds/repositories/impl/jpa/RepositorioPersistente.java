package ar.edu.utn.frba.dds.repositories.impl.jpa;

import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.utils.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RepositorioPersistente<T> implements Repositorio<T> {
    private final DAOHibernate<T> dao;

    public RepositorioPersistente(DAOHibernate<T> dao) {
        this.dao = dao;
    }

    public DAOHibernate<T> getDao() {
        return dao;
    }

    @Override
    public T agregar(T unObjeto) {
        return this.dao.agregar(unObjeto);
    }

    @Override
    public void agregar(T... objetos) {
        Arrays.stream(objetos).forEach(this.dao::agregar);
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
    public Optional<T> buscar(Integer id) {
        return this.dao.buscar(id);
    }

    @Override
    public void modificar(Integer id, T unObjeto) {
        this.dao.modificar(unObjeto);
    }

    public CriteriaBuilder criteriaBuilder(){
        return EntityManagerHelper.getEntityManager().getCriteriaBuilder();
    }
}
