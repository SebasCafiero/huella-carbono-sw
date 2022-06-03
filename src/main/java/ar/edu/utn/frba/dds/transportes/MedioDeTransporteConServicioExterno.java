package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.trayectos.Tramo;

public abstract class MedioDeTransporteConServicioExterno extends MedioDeTransporte{

    @Override
    public Float calcularDistancia(Tramo tramo) { //TODO PONER QUE LE PIDA A LA CALCULADORA DE DISTANCIAS (INTERFAZ O DIRECTO ADAPTADOR)
        Float latitudInicial = tramo.getCoordenadaInicial().getLatitud();
        Float longitudInicial = tramo.getCoordenadaInicial().getLongitud();
        Float latitudFinal = tramo.getCoordenadaFinal().getLatitud();
        Float longitudFinal = tramo.getCoordenadaFinal().getLongitud();

        return latitudFinal-latitudInicial+longitudFinal-longitudInicial;
    }

}
