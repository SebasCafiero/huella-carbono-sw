package ar.edu.utn.frba.dds.entities.medibles;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Periodo {
    private Character periodicidad;
    private Integer anio;
    private Integer mes;

    public Periodo() {}

    public Periodo(Integer anio) {
        this.periodicidad = 'A';
        this.anio = anio;
        this.mes = null;
    }

    public Periodo(Integer anio, Integer mes) {
        this.periodicidad = 'M';
        this.anio = anio;
        this.mes = mes;
    }

    public Character getPeriodicidad() {
        return periodicidad;
    }

    public Integer getAnio() {
        return anio;
    }

    public Integer getMes() {
        return mes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Periodo)) return false;
        Periodo periodo = (Periodo) o;
        return getPeriodicidad().equals(periodo.getPeriodicidad()) && getAnio().equals(periodo.getAnio()) && Objects.equals(getMes(), periodo.getMes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPeriodicidad(), getAnio(), getMes());
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "periodicidad=" + periodicidad +
                ", anio=" + anio +
                ", mes=" + mes +
                '}';
    }
}