package ar.edu.utn.frba.dds.repositories.factories;

import ar.edu.utn.frba.dds.repositories.daos.Cache;

import java.util.HashMap;

public class FactoryCache {
    private static HashMap<String, Cache> caches;

    static {
        caches = new HashMap<>();
    }

    public static <V> Cache<V> get(Class<V> valueType) {
        Cache<V> mapa;
        if(caches.containsKey(valueType.getName())) {
            mapa = caches.get(valueType.getName());
        }
        else{
            mapa = new Cache<>(valueType);
            caches.put(valueType.getName(), mapa);
        }

        return mapa;
    }
}
