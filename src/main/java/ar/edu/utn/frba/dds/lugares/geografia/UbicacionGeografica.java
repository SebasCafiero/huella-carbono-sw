package ar.edu.utn.frba.dds.lugares.geografia;

public class UbicacionGeografica {

    private Coordenada coordenada;
    private Direccion direccion;

    public UbicacionGeografica(Direccion direccion, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = direccion;
    }

    public UbicacionGeografica(String pais, String provincia, String municipio, String localidad, String calle, Integer numero, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = new Direccion(pais, provincia, municipio, localidad, calle, numero);
    }

    public UbicacionGeografica(String localidad, String calle, Integer numero, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = new Direccion(localidad, calle, numero); //TODO direccion por defecto
    }

    public UbicacionGeografica(Coordenada coordenada) { //TODO direccion por defecto
        this.coordenada = coordenada;
        this.direccion = new Direccion();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

}
