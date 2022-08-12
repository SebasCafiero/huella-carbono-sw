package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;

public class ServicioSimulado implements CalculadoraDistancias {

    @Override
    public Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal) {
        Float latitudInicial = ubicacionInicial.getCoordenada().getLatitud();
        Float longitudInicial = ubicacionInicial.getCoordenada().getLongitud();
        Float latitudFinal = ubicacionFinal.getCoordenada().getLatitud();
        Float longitudFinal = ubicacionFinal.getCoordenada().getLongitud();

        return Math.abs(latitudFinal-latitudInicial)+Math.abs(longitudFinal-longitudInicial);
    }
}
