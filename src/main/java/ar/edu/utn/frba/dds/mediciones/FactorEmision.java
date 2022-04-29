package ar.edu.utn.frba.dds.mediciones;

public class FactorEmision {
    private String categoria;
    private String unidad;
    private Float valor;

    public FactorEmision(String categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUnidad() {
        return unidad;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
}
