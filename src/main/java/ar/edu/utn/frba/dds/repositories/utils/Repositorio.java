package ar.edu.utn.frba.dds.repositories.utils;

import java.util.List;

public interface Repositorio<T> {
    void agregar(T unObjeto);
    void modificar(T unObjeto);
    void eliminar(T unObjeto);
    List<T> buscarTodos();
    T buscar(int id);
}
