package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.trayectos.CalculadoraDistanciasAdapter;
import ar.edu.utn.frba.dds.trayectos.Tramo;

public abstract class MedioDeTransporte {

    public Float calcularDistancia(Tramo tramo) {
        return new CalculadoraDistanciasAdapter().calcularDistancia(tramo.getCoordenadaInicial(),tramo.getCoordenadaFinal());
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    //    public abstract boolean matchAtributo1(String atributo);

//    public abstract boolean matchAtributo2(String atributo);

}
