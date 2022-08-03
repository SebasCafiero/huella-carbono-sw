package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.lugares.geografia.UbicacionGeografica2;

public interface CalculadoraDistancias {
    //SERIA EL ROL OBJETIVO DEL PATRON ADAPTER
    public Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal);

}
