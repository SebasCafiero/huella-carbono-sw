package ar.edu.utn.frba.dds.repositories.daos;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;

import java.util.List;

public class DAOMemoria<T> implements DAO<T> {
    private List<EntidadPersistente> entidades;

    public DAOMemoria(List<EntidadPersistente> entidades){
        this.entidades = entidades;
    }

    @Override
    public List<T> buscarTodos() {
        return (List<T>) this.entidades;
    }

    @Override
    public T buscar(int id) {
        return (T) this.entidades.get(id);

     //   return (T) this.entidades
     //           .stream()
     //           .filter(e -> e.getId() == id)
     //           .findFirst()
     //           .get();
    }

    @Override
    public void agregar(Object unObjeto) {
        this.entidades.add(EntidadPersistente.class.cast(unObjeto));
    }

    @Override
    public void modificar(Object unObjeto) {
        eliminar(unObjeto);
        agregar(unObjeto);
    }

    @Override
    public void eliminar(Object unObjeto) {
        this.entidades.remove(unObjeto);
    }
}
