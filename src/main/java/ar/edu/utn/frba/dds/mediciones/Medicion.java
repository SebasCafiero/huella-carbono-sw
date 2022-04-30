package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.time.LocalDate;

public class Medicion implements Medible {
    private String categoria;
    private String unidad;
    private Character periodicidad; // M o A
    private Float valor;
    private String periodo; // MM/AAAA o AAAA
    private LocalDate fecha; // AAAA-MM-DD

    public Medicion(String categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }

    /* LO COMENTE PARA QUE NO MOLESTE EL TEST HASTA ESTAR TERMINADO

    public Medicion(String categoria, String unidad, Float valor, String periodo) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodo = periodo;

        //Si periodo MM/AAAA:
        //this.periodicidad = 'M';
        //String anio, mes;
        //obtener anio=AAAA y mes=MM
        //this.fecha = LocalDate.parse(anio + "-" + mes + "-" + "01");


        //Si periodo AAAA:
        //this.periodicidad = 'A';
        //this.fecha = LocalDate.parse(periodo + "-01-01");

        //Se puede usar LocalDate.of(int anio, int mes, int dia);
    }
    */

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
