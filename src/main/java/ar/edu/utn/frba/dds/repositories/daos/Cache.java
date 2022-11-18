package ar.edu.utn.frba.dds.repositories.daos;

import java.util.*;

public class Cache<T> {
    private final Map<String, T> entidades;

    public Cache(Class<T> valueType) {
        this.entidades = new HashMap<>();
    }

    public Cache(HashMap<String, T> entidades) {
        this.entidades = entidades;
    }

    public Optional<T> get(String key) {
        return Optional.ofNullable(this.entidades.getOrDefault(key, null));
    }

    public Boolean containsKey(String key) {
        return entidades.containsKey(key);
    }

    public void put(String key, T value) {
        this.entidades.put(key, value);
    }

    public void delete(String unObjeto) {
        this.entidades.remove(unObjeto);
    }

}
