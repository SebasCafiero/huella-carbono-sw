package ar.edu.utn.frba.dds.repositories.impl.memory;

import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.daos.DAOMemoria;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RepositorioMemoria<T> implements Repositorio<T> {
    private final DAOMemoria<T> dao;

    public RepositorioMemoria(DAOMemoria<T> dao) {
        this.dao = dao;
    }

    public DAOMemoria<T> getDao() {
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
    public T modificar(T unObjeto) {
        return this.dao.modificar(unObjeto);
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
        this.dao.modificar(id,unObjeto);
    }

    @Override
    public Optional<T> getReferenceById(Integer id) {
        return this.dao.getReferenceById(id);
    }

    @Override
    public void sync(T entity) { //todo en memoria no se necesita sincronizar
    }
}
