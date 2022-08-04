package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.geografia.UbicacionGeografica;

public interface CalculadoraDistancias {
    //SERIA EL ROL OBJETIVO DEL PATRON ADAPTER
    Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal);

}
