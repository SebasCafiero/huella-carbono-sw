package ar.edu.utn.frba.dds.entities.mediciones;

public class Periodo {
    private Character periodicidad;
    private Integer anio;
    private Integer mes;

    public Periodo(Integer anio, Integer mes) {
        this.periodicidad = 'M';
        this.anio = anio;
        this.mes = mes;
    }

    public Periodo(Integer anio) {
        this.periodicidad = 'A';
        this.anio = anio;
        this.mes = 0; //TODO principio isp
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

    public void setPeriodicidad(Character periodicidad) {
        this.periodicidad = periodicidad;
    }
}



/*public class Periodo {
    private Character periodicidad;
    private String fecha;

    public Periodo(Integer anio, Integer mes) {
        this.periodicidad = 'M';
        this.fecha = anio.toString()+mes.toString();
    }

    public Periodo(Integer anio) {
        this.periodicidad = 'A';
        this.fecha = anio.toString();
    }

    public Character getPeriodicidad() {
        return periodicidad;
    }

    public Integer obtenerAnio() {
        return Integer.parseInt(fecha.substring(0,4));
    }

    public Integer obtenerMes() {
        if(periodicidad == 'M')
            return Integer.parseInt(fecha.substring(4));
        else
            return 0; //TODO principio isp
    }
}*/
