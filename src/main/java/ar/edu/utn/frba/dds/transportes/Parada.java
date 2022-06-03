package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.lugares.Coordenada;

public class Parada {
    private Coordenada coordenada;
    private Float distanciaAnterior;
    private Float distanciaProxima;
//TODO VER SI NO CONVIENE CONOCER DIRECTAMENTE LA PARADA ANTERIOR Y PROXIMA?
//La distanciaProxima de una parada deberia ser igual a la distanciaAnterior de la parada siguiente (VALIDAR)

    public Parada(Coordenada coordenada, Float distanciaAnterior, Float distanciaProxima){
        this.coordenada = coordenada;
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public Float getDistanciaAnterior() {
        return distanciaAnterior;
    }

    public Float getDistanciaProxima() {
        return distanciaProxima;
    }
}
