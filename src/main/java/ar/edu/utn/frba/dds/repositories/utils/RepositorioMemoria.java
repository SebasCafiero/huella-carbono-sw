package ar.edu.utn.frba.dds.repositories.utils;

import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.List;

public class RepositorioMemoria<T> implements Repositorio<T> {
    private final DAOMemoria<T> dao;

    public RepositorioMemoria(DAOMemoria<T> dao) {
        this.dao = dao;
    }

    public DAOMemoria<T> getDao() {
        return dao;
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
}