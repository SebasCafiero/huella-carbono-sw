package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.CalculadoraDistancias;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ServicioSimulado;

public abstract class MedioDeTransporte extends EntidadPersistente {

    public Float calcularDistancia(Tramo tramo) {
//        CalculadoraDistancias servicioContratado = new AdaptadorServicioDDSTPA(); // TODO ver de definir en MedioDeTransporte
        CalculadoraDistancias servicioContratado = new ServicioSimulado();

        return servicioContratado.calcularDistancia(tramo.getUbicacionInicial(), tramo.getUbicacionFinal());
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

    public abstract String getCategoria();
}
