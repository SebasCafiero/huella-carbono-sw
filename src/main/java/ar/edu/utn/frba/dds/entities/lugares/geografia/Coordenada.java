package ar.edu.utn.frba.dds.entities.lugares.geografia;

import ar.edu.utn.frba.dds.server.SystemProperties;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordenada {

    @Column(name = "latitud")
    private Float latitud;

    @Column(name = "longitud")
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

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public boolean esIgualAOtraCoordenada(Coordenada otraCoordenada) {
        return Math.abs(otraCoordenada.getLatitud() - this.latitud) < SystemProperties.getDelta() &&
                Math.abs(otraCoordenada.getLongitud() - this.longitud) < SystemProperties.getDelta();
    }

    @Override
    public String toString() {
        return "Coordenada{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
