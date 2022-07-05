package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.excepciones.FechaException;
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

    public Medicion(Categoria categoria, String unidad, float valor, char periodicidad, LocalDate periodo) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodicidad = periodicidad;
        this.fecha = periodo;
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
        return categoria.toString();
    }
}
