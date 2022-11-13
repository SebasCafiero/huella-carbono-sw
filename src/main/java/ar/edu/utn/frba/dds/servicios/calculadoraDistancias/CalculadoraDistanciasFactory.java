package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

import ar.edu.utn.frba.dds.server.SystemProperties;

public class CalculadoraDistanciasFactory {
    public static CalculadoraDistancias get() {
        if(SystemProperties.isCalculadoraDistanciasMockEnabled()) {
            return new ServicioSimulado();
        } else {
            return new AdaptadorServicioDDSTPA();
        }
    }
}
