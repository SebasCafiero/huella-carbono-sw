package ar.edu.utn.frba.dds.entities.lugares.geografia;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordenada {

    @Column(name = "latitud")
    private Float latitud;

    @Column(name = "longitud")
    private Float longitud;

    public Coordenada() {
    }

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

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public boolean esIgualAOtraCoordenada(Coordenada otraCoordenada){
        return otraCoordenada.getLatitud().equals(this.latitud)
                && otraCoordenada.getLongitud().equals(this.longitud);
    }

    @Override
    public String toString() {
        return "Coordenada{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
