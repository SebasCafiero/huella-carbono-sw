package ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias;

import ar.edu.utn.frba.dds.server.SystemProperties;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.AdaptadorServicioDDSTPA;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.mock.ServicioSimulado;

public class CalculadoraDistanciasFactory {
    public static CalculadoraDistancias get() {
        if(SystemProperties.isCalculadoraDistanciasMockEnabled()) {
            return new ServicioSimulado();
        } else {
            return new AdaptadorServicioDDSTPA();
        }
    }
}
