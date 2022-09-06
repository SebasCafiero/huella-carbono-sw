package ar.edu.utn.frba.dds.entities.mediciones;

import java.time.LocalDate;
import java.util.List;

public class BatchMedicion {
    private Integer id;
    private List<Medicion> mediciones;
    private LocalDate fecha;


    public BatchMedicion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setFecha(String fecha) {this.fecha = LocalDate.parse(fecha);}

    @Override
    public String toString() {
        return '\n' + "BatchMedicion { " + '\n' +
                "  Mediciones = " + mediciones.toString() + '\n' +
                "fecha = " + fecha.toString()  +
                '}';
    }
}

