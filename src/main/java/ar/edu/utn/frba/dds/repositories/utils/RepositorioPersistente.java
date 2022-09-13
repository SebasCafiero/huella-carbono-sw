package ar.edu.utn.frba.dds.repositories.utils;

import ar.edu.utn.frba.dds.repositories.daos.DAOHibernate;
import ar.edu.utn.frba.dds.repositories.daos.DAOJPA;

import java.util.List;

public class RepositorioPersistente<T> implements Repositorio<T> {
//    private final DAOJPA<T> dao;
//    public RepositorioPersistente(DAOJPA<T> dao) {
//        this.dao = dao;
//    }

    private final DAOHibernate<T> dao;
    public RepositorioPersistente(DAOHibernate<T> dao) {
        this.dao = dao;
    }

    @Override
    public void agregar(T unObjeto) {
        this.dao.agregar(unObjeto);
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

    }
}
