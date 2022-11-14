package ar.edu.utn.frba.dds.repositories.daos;

import java.lang.reflect.Type;
import java.util.*;

public class Cache<T, ID> {
    private final Map<T, ID> entidades;

    public Cache(Class<T> keyType, Class<ID> valueType) {
        this.entidades = new HashMap<>();
    }

    public Cache(Class<T> clazz, HashMap<T, ID> entidades) {
        this.entidades = entidades;
    }

    public ID get(T key) {
        return this.entidades.getOrDefault(key, null);
    }

    public Boolean containsKey(T key) {
        return entidades.containsKey(key);
    }

    public void put(T key, ID value) {
        this.entidades.put(key, value);
    }

    public void delete(T unObjeto) {
        this.entidades.remove(unObjeto);
    }

}
