package ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias;

import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;

public interface CalculadoraDistancias {
    Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal);

}
