package ar.edu.utn.frba.dds.lugares;

public class UbicacionGeografica { //TODO VER SI LO USAMOS PARA LA ORG Y LOS HOGARES (check diagrama)
    private String lugar;
    private Coordenada coordenada;

    public UbicacionGeografica(String lugar, Coordenada coordenada) {
        this.lugar = lugar;
        this.coordenada = coordenada;
    }

    public String getLugar() {
        return lugar;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

}
