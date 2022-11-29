package ar.edu.utn.frba.dds.interfaces.input.json;

public class PeriodoJSONDTO {
    private Character periodicidad;
    private Integer anio;
    private Integer mes;

    public PeriodoJSONDTO() {
    }

    public PeriodoJSONDTO(Integer anio, Integer mes) {
        if (mes == null) {
            this.periodicidad = 'A';
        } else {
            this.periodicidad = 'M';
            this.mes = mes;
        }
        this.anio = anio;
    }

    public PeriodoJSONDTO(Integer anio) {
        this.periodicidad = 'A';
        this.anio = anio;
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
