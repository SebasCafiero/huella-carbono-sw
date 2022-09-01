package ar.edu.utn.frba.dds.entities.mediciones;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.time.LocalDate;

public class Medicion extends EntidadPersistente implements Medible {
    private Categoria categoria;
    private String unidad;
    private Float valor;
//    private Character periodicidad; // M o A
//    private LocalDate fecha; // AAAA-MM-DD
    private Periodo periodo;

    public Medicion(Categoria categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        //TODO
    }

    public Medicion() {} //TODO

    public Medicion(Categoria categoria, String unidad, float valor, Periodo periodo) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodo = periodo;
    }

    public Character getPeriodicidad() {
        return this.periodo.getPeriodicidad();
    }

//    public LocalDate getFecha() {
//        return fecha;
//    }

//    public Integer getAnio(){
//        return fecha.getYear();
//    }
//
//    public Integer getMes(){
//        return fecha.getMonthValue();
//    }

    public Integer getAnio() {
//        return this.periodo.obtenerAnio();
        return periodo.getAnio();
    }

    public Integer getMes() {
//        return this.periodo.obtenerMes();
        return periodo.getMes();
    }

    @Override
    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public void setPeriodicidad(Character periodicidad) {
        this.periodo.setPeriodicidad(periodicidad);
    }

//    public void setFecha(LocalDate fecha) {
//        this.fecha = fecha;
//    }

    public void setFecha(Periodo fecha) {
        this.periodo = fecha;
    }


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
                "valor = " + valor.toString() + '\'' +
                "unidad = " + unidad + '\'' +
                '}';
    }

    public boolean perteneceAPeriodo(Integer anio, Integer mes) {
        if(getPeriodicidad().equals('A')) {
            return getAnio().equals(anio);
        } else if(getPeriodicidad().equals('M')) {
            return getAnio().equals(anio) && getMes().equals(mes);
        }
        return false;
    }
}
