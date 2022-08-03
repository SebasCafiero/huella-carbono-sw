package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.lugares.geografia.UbicacionGeografica;

public class Parada {
    private UbicacionGeografica ubicacion;
    private Float distanciaAnterior;
    private Float distanciaProxima;
//La distanciaProxima de una parada deberia ser igual a la distanciaAnterior de la parada siguiente (VALIDAR)

    public Parada(Direccion lugar, Coordenada coordenada, Float distanciaAnterior, Float distanciaProxima){
        this.ubicacion = new UbicacionGeografica(lugar, coordenada);
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Parada(UbicacionGeografica ubicacion, Float distanciaAnterior, Float distanciaProxima){
        this.ubicacion = ubicacion;
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Parada(Coordenada coordenada, Float distanciaAnterior, Float distanciaProxima) {
        this.ubicacion = new UbicacionGeografica(coordenada);
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Coordenada getCoordenada() {
        return ubicacion.getCoordenada();
    }

    public Float getDistanciaAnterior() {
        return distanciaAnterior;
    }

    public Float getDistanciaProxima() {
        return distanciaProxima;
    }
}
