package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.lugares.geografia.UbicacionGeografica2;

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
