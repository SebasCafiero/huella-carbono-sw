package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.excepciones.FechaException;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.time.LocalDate;

public class Medicion implements Medible {
    private Categoria categoria;
    private String unidad;
    private Character periodicidad; // M o A
    private Float valor;
    private String periodo; // MM/AAAA o AAAA
    private LocalDate fecha; // AAAA-MM-DD

    public Medicion(Categoria categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }


    public Medicion(Categoria categoria, String unidad, Float valor, String periodicidad, String periodo) throws Exception {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodicidad = periodicidad.charAt(0);

        if(this.periodicidad == 'M'){
            String[] mesYanio = periodo.split("/");
            this.fecha = LocalDate.parse(mesYanio[1]+"-"+mesYanio[0]+"-01");
        }
        else if(this.periodicidad == 'A'){
            this.fecha = LocalDate.parse(periodo + "-01-01");
            //this.fecha = LocalDate.of(Integer.parseInt(periodo),1,1);
        }
        else throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO
    }

    public Medicion() {

    }
    @Override
    public String getCategoria() {
        return categoria.toString();
    }
    public void setCategoria (Categoria categoria) { this.categoria = categoria;}

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

    public Character getPeriodicidad() {
        return periodicidad;
    }
    public void setPeriodicidad(Character periodicidad) {this.periodicidad = periodicidad;}

    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {this.periodo = periodo;}

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    @Override
    public String toString() {
        return "Medicion{" +
                "valor='" + valor.toString() + '\'' +
                '}';
    }

    public void setPeriodicidad(String periodicidad) {
    }//---> ver como completar

    public void setFecha(int fecha) {
    }//---> ver como completar
}
