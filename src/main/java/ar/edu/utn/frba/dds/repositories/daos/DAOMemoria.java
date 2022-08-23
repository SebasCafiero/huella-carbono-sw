package ar.edu.utn.frba.dds.repositories.daos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DAOMemoria<T> implements DAO<T> {
    private final List<T> entidades;
    public Class<T> persistentClass;
    private Integer id;

    public DAOMemoria(Class<T> clazz, List<T> entidades){
        this.entidades = entidades;
        this.persistentClass = clazz;
        this.id = 1;
//        this.persistentClass.getName();
//        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[0];

//            System.out.println(Arrays.stream(this.getClass().getFields()).map(fi -> fi.getName()).collect(Collectors.toList()));

    }

    @Override
    public List<T> buscarTodos() {
        return this.entidades;
    }

    @Override
    public T buscar(int id) {
        Method getId = obtenerMetodo("getId");
        return this.entidades.stream()
                .filter(e -> invocarGetter(getId, e).equals(id))
                .findFirst()
                .get();
//        return this.entidades
//                .stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .get();
    }

    public List<T> buscar(Predicate<T> condicion) {
        return this.entidades
                .stream()
                .filter(condicion)
                .collect(Collectors.toList());
    }

    @Override
    public void agregar(T unObjeto) {
        invocarSetter(obtenerMetodo("setId"), unObjeto, id);
        id++;
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

    private Method obtenerMetodo(String nombre) {
        Optional<Method> getterId = Arrays.stream(persistentClass.getMethods()).filter(me -> me.getName().equals(nombre)).findFirst();
        if (!getterId.isPresent()) {
            throw new EntidadSinPrimaryKey();
        }
        return getterId.get();
    }

    private Integer invocarGetter(Method metodo, T entidad) {
        Integer id;
        try {
            id = (Integer) metodo.invoke(entidad);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException();
        }
        return id;
    }

    private void invocarSetter(Method metodo, T entidad, Integer id) {
        try {
            metodo.invoke(entidad, id);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException();
        }
    }

    private Type getType(T entidad) {
        return entidad.getClass();
    }

}
