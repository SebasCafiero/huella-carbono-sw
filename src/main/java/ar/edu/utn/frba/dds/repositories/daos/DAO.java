package ar.edu.utn.frba.dds.repositories.daos;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> buscarTodos();
    Optional<T> buscar(Integer id);
    T agregar(T unObjeto);
    T modificar(T unObjeto);
    void eliminar(T unObjeto);

    Optional<T> getReferenceById(Integer id);
}

