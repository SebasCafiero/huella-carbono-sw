package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.trayectos.CalculadoraDistanciasAdapter;
import ar.edu.utn.frba.dds.trayectos.Tramo;

public abstract class MedioDeTransporteConServicioExterno extends MedioDeTransporte{
    
    @Override
    public Float calcularDistancia(Tramo tramo) {
        return new CalculadoraDistanciasAdapter().calcularDistancia(tramo.getCoordenadaInicial(),tramo.getCoordenadaFinal());
    }
}
