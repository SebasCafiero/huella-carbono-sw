package ar.edu.utn.frba.dds.lugares.geografia;

public class Coordenada {
    private Float latitud;
    private Float longitud;

    public Coordenada(Float latitud, Float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public boolean esIgualAOtraCoordenada(Coordenada otraCoordenada){
        return otraCoordenada.getLatitud().equals(this.latitud)
                && otraCoordenada.getLongitud().equals(this.longitud);
    }
}
