package ar.edu.utn.frba.dds.entities.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.time.LocalDate;

public class Medicion implements Medible {
    private Categoria categoria;
    private String unidad;
    private Character periodicidad; // M o A
    private Float valor;
    private LocalDate fecha; // AAAA-MM-DD

    public Medicion(Categoria categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }

    public Medicion() {}

    public Medicion(Categoria categoria, String unidad, float valor, char periodicidad, LocalDate periodo) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodicidad = periodicidad;
        this.fecha = periodo;
    }

    public Character getPeriodicidad() {
        return periodicidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getAnio(){
        return fecha.getYear();
    }

    public Integer getMes(){
        return fecha.getMonthValue();
    }

    @Override
    public String getUnidad() {
        return unidad;
    }
    public void setUnidad (String unidad) { this.unidad = unidad;}

    @Override
    public Float getValor() {
        return valor;
    }
    public void setValor (Float valor) { this.valor = valor;}

    public void setPeriodicidad(Character periodicidad) {this.periodicidad = periodicidad;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    @Override
    public String getCategoria() {
        return categoria.toString();
    }

    public void setCategoria (Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Medicion{" +
                "valor='" + valor.toString() + '\'' +
                '}';
    }
}
