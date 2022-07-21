package ar.edu.utn.frba.dds.mediciones;

public class FactorEmision {
    private Categoria categoria;
    private String unidad;
    private Float valor;

    public FactorEmision(Categoria categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }

    public String getCategoria() {
        return this.categoria.toString();
    }
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}

    public String getUnidad() {
        return unidad;
    }
    public void setUnidad(String unidad) {this.unidad = unidad;}

    public Float getValor() {
        return valor;
    }
    public void setValor(Float valor) {
        this.valor = valor;
    }


}
