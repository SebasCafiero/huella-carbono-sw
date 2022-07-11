package ar.edu.utn.frba.dds.mediciones;

import java.time.LocalDate;
import java.util.List;

public class BatchMediciones {
    private LocalDate fecha;
    private List<Medicion> mediciones;

    public BatchMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
        this.fecha = LocalDate.now();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }
}
