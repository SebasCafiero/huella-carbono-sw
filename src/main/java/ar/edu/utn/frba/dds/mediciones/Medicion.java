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
