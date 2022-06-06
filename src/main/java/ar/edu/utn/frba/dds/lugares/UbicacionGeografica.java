package ar.edu.utn.frba.dds.lugares;

public class UbicacionGeografica {
    private String localidad;
    private Coordenada coordenada;

    public UbicacionGeografica(String lugar, Coordenada coordenada) {
        this.localidad = lugar;
        this.coordenada = coordenada;
    }
    public UbicacionGeografica(String lugar, Float latitud, Float longitud){
        this.localidad = lugar;
        this.coordenada = new Coordenada(latitud,longitud);
    }

    public String getLocalidad() {
        return localidad;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

}
