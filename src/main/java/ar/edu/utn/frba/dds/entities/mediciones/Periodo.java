package ar.edu.utn.frba.dds.entities.mediciones;

public class Periodo {
    private Character periodicidad;
    private Integer anio;
    private Integer mes;

    public Periodo() { }

    public Periodo(Character periodicidad, Integer anio, Integer mes) {
        this.periodicidad = periodicidad;
        this.anio = anio;
        this.mes = mes;
    }

    public Character getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Character periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }
}
