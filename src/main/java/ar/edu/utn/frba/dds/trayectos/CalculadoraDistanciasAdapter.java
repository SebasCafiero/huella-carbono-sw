package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;

public class CalculadoraDistanciasAdapter implements CalculadoraDistancias{

    private Object servicioExterno;

    @Override
    public Float    calcularDistancia(Coordenada coordenadaInicial, Coordenada coordenadaFinal) {
        Float latitudInicial = coordenadaInicial.getLatitud();
        Float longitudInicial = coordenadaInicial.getLongitud();
        Float latitudFinal = coordenadaFinal.getLatitud();
        Float longitudFinal = coordenadaFinal.getLongitud();
        //return servicioExterno.distancia(latitudInicial,longitudInicial,latitudFinal,longitudFinal); //Devuelve unidad KM
        return latitudFinal-latitudInicial+longitudFinal-longitudInicial;
    }
}
