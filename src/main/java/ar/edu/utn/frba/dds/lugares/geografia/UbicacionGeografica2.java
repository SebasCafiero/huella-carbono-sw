package ar.edu.utn.frba.dds.lugares.geografia;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.lugares.Direccion;

public class UbicacionGeografica2 {

    private Coordenada coordenada;
    private Direccion2 direccion;

    public UbicacionGeografica2(Direccion2 direccion, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = direccion;
    }

    public UbicacionGeografica2(String pais, String provincia, String municipio, String localidad, String calle, Integer numero, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = new Direccion2(pais, provincia, municipio, localidad, calle, numero);
    }

    public UbicacionGeografica2(String localidad, String calle, Integer numero, Coordenada coordenada) {
        this.coordenada = coordenada;
        this.direccion = new Direccion2(localidad, calle, numero);
    }

    public Direccion2 getDireccion() {
        return direccion;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

}
