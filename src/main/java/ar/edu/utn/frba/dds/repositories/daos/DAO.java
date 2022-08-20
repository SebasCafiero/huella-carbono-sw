package ar.edu.utn.frba.dds.repositories.daos;

import java.util.List;

public interface DAO<T> {
    List<T> buscarTodos();
    T buscar(int id);
    void agregar(Object unObjeto);
    void modificar(Object unObjeto1, Object unObjeto2);
    void eliminar(Object unObjeto);
}
