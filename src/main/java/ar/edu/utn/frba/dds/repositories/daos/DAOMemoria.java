package ar.edu.utn.frba.dds.repositories.daos;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DAOMemoria<T extends EntidadPersistente> implements DAO<T> {
    private final List<T> entidades;

    public DAOMemoria(List<T> entidades){
        this.entidades = entidades;
    }

    @Override
    public List<T> buscarTodos() {
        return this.entidades;
    }

    @Override
    public T buscar(int id) {
        return this.entidades
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .get();
    }

    public List<T> buscar(Predicate<T> condicion) {
        return this.entidades
                .stream()
                .filter(condicion)
                .collect(Collectors.toList());
    }

    @Override
    public void agregar(T unObjeto) {
        this.entidades.add(unObjeto);
    }

    @Override
    public void modificar(T unObjeto) {
        eliminar(unObjeto);
        agregar(unObjeto);
    }

    @Override
    public void eliminar(T unObjeto) {
        this.entidades.remove(unObjeto);
    }
}
