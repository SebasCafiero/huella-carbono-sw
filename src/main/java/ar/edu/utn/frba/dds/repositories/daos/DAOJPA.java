package ar.edu.utn.frba.dds.repositories.daos;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;

import java.util.List;

public class DAOJPA<T extends EntidadPersistente> implements DAO<T> {
    private final List<T> entidades;

    public DAOJPA(List<T> entidades){
        this.entidades = entidades;
    }

    @Override
    public List<T> buscarTodos() {
        return (List<T>) this.entidades;
    }

    @Override
    public T buscar(int id) {
        return (T) this.entidades
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void agregar(T unObjeto) {
        this.entidades.add((T) unObjeto);
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
