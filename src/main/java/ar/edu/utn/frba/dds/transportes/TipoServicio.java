package ar.edu.utn.frba.dds.transportes;

public class TipoServicio {
    private String nombre;

    public TipoServicio(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

/* Ejemplos de uso:
* taxi = new TipoServicio("taxi");
* remis = new TipoServicio("remis");
* uber = new TipoServicio("uber");
* didi = new TipoServicio("didi");
* cabify = new TipoServicio("cabify");
* */
