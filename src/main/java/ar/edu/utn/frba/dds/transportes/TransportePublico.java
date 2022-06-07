package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.trayectos.Tramo;

import java.util.ArrayList;
import java.util.Collections;

public class TransportePublico extends MedioDeTransporte{
    private TipoTransportePublico tipo;
    private ArrayList<Parada> paradas;
    private String linea;

    public TransportePublico(TipoTransportePublico tipo, String linea){
        this.tipo = tipo;
        this.linea = linea;
        this.paradas = new ArrayList<>();
    }

    public void agregarParada(Parada parada){
        this.paradas.add(parada);
    }
    public void agregarParadas(Parada... paradas){
        Collections.addAll(this.paradas,paradas);
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

    private Parada buscarParada(Coordenada coordenada) {
        //TODO Por el momento busca la parada que coincida, quizás debería buscar la mas cercana
        return this.paradas.stream()
                .filter(parada -> parada.getCoordenada().esIgualAOtraCoordenada(coordenada))
                .findFirst()
                .orElse(null);
    }

    public String getLinea() {
        return linea;
    }

    public TipoTransportePublico getTipo() {
        return tipo;
    }
    public Boolean equals( TransportePublico trasportePublico){
        return this.tipo.equals(trasportePublico.getTipo()) && this.linea.equals(trasportePublico.getLinea());
    }
}
