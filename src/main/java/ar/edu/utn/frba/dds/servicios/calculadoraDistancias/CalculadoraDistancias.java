package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;

public interface CalculadoraDistancias {
    Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal);

}
