package ar.edu.utn.frba.dds.repositories.daos;

import java.util.List;

public interface DAO<T> {
    List<T> buscarTodos();
    T buscar(int id);
    T agregar(T unObjeto);
    void modificar(T unObjeto);
    void eliminar(T unObjeto);
}
