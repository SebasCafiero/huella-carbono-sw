package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.Coordenada;

public class ServicioSimulado implements CalculadoraDistancias {

    @Override
    public Float calcularDistancia(Coordenada coordenadaInicial, Coordenada coordenadaFinal) {
        Float latitudInicial = coordenadaInicial.getLatitud();
        Float longitudInicial = coordenadaInicial.getLongitud();
        Float latitudFinal = coordenadaFinal.getLatitud();
        Float longitudFinal = coordenadaFinal.getLongitud();

        return Math.abs(latitudFinal-latitudInicial)+Math.abs(longitudFinal-longitudInicial);
    }
}
