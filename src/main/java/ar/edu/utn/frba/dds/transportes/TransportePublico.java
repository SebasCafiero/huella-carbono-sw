package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TransportePublico extends MedioDeTransporte {
    private TipoTransportePublico tipo;
    private LinkedList<Parada> paradas;
    private String linea;

    public TransportePublico(TipoTransportePublico tipo, String linea){
        this.tipo = tipo;
        this.linea = linea;
        this.paradas = new LinkedList<>();
    }

    public void agregarParada(Parada parada){
        this.paradas.add(parada);
    }

    public void agregarParadas(Parada... paradas){
        Collections.addAll(this.paradas,paradas);
    }

    public LinkedList<Parada> getParadas() {
        return paradas;
    }

    @Override
    public Float calcularDistancia(Tramo tramo) { //Quizas una interface que haga el calculo, ver calculadoraDistancias_Objetivo del diagrama
        Parada paradaInicial = buscarParada(tramo.getCoordenadaInicial());
        if (paradaInicial == null)
            System.out.println("ERROR DE COORDENADAS"); //TODO VER DE USAR EXCEPCIONES
        //Parada paradaFinal = buscarParada(tramo.getCoordenadaFinal());
        //Se podria validar que la distancia proxima de la parada inicial sea igual a la distancia anterior de la parada final.

        return paradaInicial.getDistanciaProxima();
    }

    @Override
    public boolean matchAtributo1(String atributo) {
        try {
            TipoTransportePublico tipo = TipoTransportePublico.valueOf(atributo);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean matchAtributo2(String atributo) {
        return atributo.equals(linea);
    }

    private Parada buscarParada(Coordenada coordenada) {
        //TODO Por el momento busca la parada que coincida, quizás debería buscar la mas cercana
        return this.paradas.stream()
                .filter(parada -> parada.getCoordenada().esIgualAOtraCoordenada(coordenada))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "publico";
    }
}
