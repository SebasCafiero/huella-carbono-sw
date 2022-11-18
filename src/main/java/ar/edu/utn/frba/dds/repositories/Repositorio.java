package ar.edu.utn.frba.dds.repositories;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {
    T agregar(T unObjeto);

    void agregar(T... objetos);
    void modificar(T unObjeto);
    void eliminar(T unObjeto);
    List<T> buscarTodos();
    T buscar(Integer id);

    void modificar(Integer id,T unObjeto);
}