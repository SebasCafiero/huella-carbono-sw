package ar.edu.utn.frba.dds.repositories;

import java.util.HashMap;
import java.util.Map;

public class RepoFactores {
    private static RepoFactores instance = null;
    private final Map<String, Float> factorEmisionMap;

    public RepoFactores() {
        this.factorEmisionMap = new HashMap<>();
    }

    public static RepoFactores getInstance() {
        if(instance == null) {
            instance = new RepoFactores();
        }
        return instance;
    }

    public boolean existe(String categoria) {
        return factorEmisionMap.containsKey(categoria);
    }

    public Float getValor(String categoria) {
        return factorEmisionMap.get(categoria);
    }

    public void setValor(String categoria, Float valor) {
        this.factorEmisionMap.put(categoria, valor);
    }

    public void putAll(Map<String, Float> parametrosSistema) {
        this.factorEmisionMap.putAll(parametrosSistema);
    }
}
