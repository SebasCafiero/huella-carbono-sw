package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;

public interface CalculadoraDistancias {
    //SERIA EL ROL OBJETIVO DEL PATRON ADAPTER
    Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal);

}
