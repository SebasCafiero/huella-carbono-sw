package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.Coordenada;

public interface CalculadoraDistancias {
    //SERIA EL ROL OBJETIVO DEL PATRON ADAPTER
    public Float calcularDistancia(Coordenada coordenadaInicial, Coordenada coordenadaFinal);

}
