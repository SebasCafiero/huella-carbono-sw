package ar.edu.utn.frba.dds.entities.medibles;

import javax.persistence.Embeddable;

@Embeddable
public class Periodo {
    private Character periodicidad;
    private Integer anio;
    private Integer mes;

    public Periodo() {}

    public Periodo(Integer anio) {
        this.periodicidad = 'A';
        this.anio = anio;
        this.mes = 1; //TODO principio isp
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

    public boolean incluye(Integer anio, Integer mes) {
        if(this.periodicidad.equals('A')) {
            return this.anio.equals(anio);
        } else if(this.periodicidad.equals('M')) {
            return this.anio.equals(anio) && this.mes.equals(mes);
        }
        return false;
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