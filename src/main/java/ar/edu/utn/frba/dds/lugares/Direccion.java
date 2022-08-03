package ar.edu.utn.frba.dds.lugares;

public class Direccion {
    private String calle;
    private Integer numero;

    public Direccion(String calle, Integer numero) {
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion(Coordenada coordenada) {
        this.calle = obtenerCalle(coordenada);
        this.numero = obtenerNumero(coordenada);
    }

    public Integer getNumero() {
        return numero;
    }

    public String getCalle() {
        return calle;
    }

    //TODO
    public String obtenerCalle(Coordenada unaCoordenada) {
        return "";
    }

    //TODO
    public Integer obtenerNumero(Coordenada unaCoordenada) {
        return 0;
    }

    //TODO
    public Coordenada obtenerCoordenada(Direccion unaDireccion) {
        return new Coordenada(1F, 1F);
    }
}
