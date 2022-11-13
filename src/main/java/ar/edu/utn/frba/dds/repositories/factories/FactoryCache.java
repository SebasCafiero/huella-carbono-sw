package ar.edu.utn.frba.dds.repositories.factories;

import ar.edu.utn.frba.dds.repositories.daos.Cache;

import java.util.HashMap;

public class FactoryCache {
    private static HashMap<String, Cache> caches;

    static {
        caches = new HashMap<>();
    }

    public static <K, V> Cache<K, V> get(Class<K> keyType, Class<V> valueType) {
        Cache<K, V> mapa;
        if(caches.containsKey(keyType.getName())) {
            mapa = caches.get(keyType.getName());
        }
        else{
            mapa = new Cache<>(keyType, valueType);

            caches.put(keyType.getName(), mapa);
        }

        return mapa;
    }
}
