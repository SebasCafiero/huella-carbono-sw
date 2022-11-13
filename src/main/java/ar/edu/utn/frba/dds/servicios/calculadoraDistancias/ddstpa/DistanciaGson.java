package ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ddstpa;

public class DistanciaGson {
    private Float valor; //Recibe String pero Gson parsea solo
    private String unidad;

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
