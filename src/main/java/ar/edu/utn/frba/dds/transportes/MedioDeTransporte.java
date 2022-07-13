package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.trayectos.servicios.AdaptadorServicioDDSTPA;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.servicios.CalculadoraDistancias;
import ar.edu.utn.frba.dds.trayectos.servicios.ServicioSimulado;

public abstract class MedioDeTransporte {

    public Float calcularDistancia(Tramo tramo) {
//        CalculadoraDistancias servicioContratado = new AdaptadorServicioDDSTPA();
        CalculadoraDistancias servicioContratado = new ServicioSimulado();

        return servicioContratado.calcularDistancia(tramo.getCoordenadaInicial(),tramo.getCoordenadaFinal());
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

    public abstract String getCategoria();
}
