package ar.edu.utn.frba.dds.interfaces.input.json;

public class FactorEmisionJSONDTO {
    public CategoriaJSONDTO categoria;
    public String unidad;
    public Float valor;

    public CategoriaJSONDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaJSONDTO categoria) {
        this.categoria = categoria;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
}
