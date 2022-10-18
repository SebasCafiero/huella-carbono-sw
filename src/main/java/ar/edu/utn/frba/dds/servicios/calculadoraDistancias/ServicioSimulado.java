package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.server.SystemProperties;

public class ServicioSimulado implements CalculadoraDistancias {

    @Override
    public Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal) {
        Float latitudInicial = ubicacionInicial.getCoordenada().getLatitud();
        Float longitudInicial = ubicacionInicial.getCoordenada().getLongitud();
        Float latitudFinal = ubicacionFinal.getCoordenada().getLatitud();
        Float longitudFinal = ubicacionFinal.getCoordenada().getLongitud();

//        throw new RuntimeException("Servicio Sin Ganas de Trabajar");
        double coeficiente = SystemProperties.getCoeficienteGradoKm();
        double distanciaEnGrados = Math.sqrt(
                Math.pow(latitudFinal - latitudInicial, 2) + Math.pow(longitudFinal - longitudInicial, 2));

        return (float) (coeficiente * distanciaEnGrados);
    }
}
