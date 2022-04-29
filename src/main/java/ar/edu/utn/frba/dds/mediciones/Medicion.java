package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

public class Medicion implements Medible {
    private String categoria;
    private String unidad;
    private Character periodicidad;
    private Float valor;

    public Medicion(String categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }

    @Override
    public String getUnidad() {
        return unidad;
    }

    @Override
    public Float getValor() {
        return valor;
    }

    @Override
    public String getCategoria() {
        return categoria;
    }
}
