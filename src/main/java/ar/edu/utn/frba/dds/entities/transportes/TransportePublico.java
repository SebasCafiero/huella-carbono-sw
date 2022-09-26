package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class TransportePublico extends MedioDeTransporte {
    private final TipoTransportePublico tipo;
    private final LinkedList<Parada> paradas;
    private final String linea;

    public TransportePublico(TipoTransportePublico tipo, String linea) {
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

//    @Override
//    public Float calcularDistancia(Tramo tramo) { //Quizas una interface que haga el calculo, ver calculadoraDistancias_Objetivo del diagrama
//        Parada paradaInicial = buscarParada(tramo.getUbicacionInicial().getCoordenada());
//        if (paradaInicial == null)
//            System.out.println("ERROR DE COORDENADAS"); //TODO VER DE USAR EXCEPCIONES
//        //Parada paradaFinal = buscarParada(tramo.getCoordenadaFinal());
//        //Se podria validar que la distancia proxima de la parada inicial sea igual a la distancia anterior de la parada final.
//
//        return paradaInicial.getDistanciaProxima();
//    }

    @Override
    public Float calcularDistancia(Tramo tramo) {
        Parada paradaInicial = buscarParada(tramo.getUbicacionInicial().getCoordenada());
        Parada paradaFinal = buscarParada(tramo.getUbicacionFinal().getCoordenada());
        if (paradaInicial == null || paradaFinal == null) {
            throw new TransportePublicoSinParadaException();
        }

        int nroParadaInicial = paradas.indexOf(paradaInicial);
        int nroParadaFinal = paradas.indexOf(paradaFinal);

        if(nroParadaInicial < nroParadaFinal) {
            return (float) paradas.subList(nroParadaInicial, nroParadaFinal).stream()
                    .mapToDouble(Parada::getDistanciaProxima).reduce(0, Double::sum);
        } else {
            return (float) paradas.subList(nroParadaFinal + 1, nroParadaInicial + 1).stream()
                    .mapToDouble(Parada::getDistanciaAnterior).reduce(0, Double::sum);
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TransportePublico other = (TransportePublico) obj;
        if (!Objects.equals(tipo, other.tipo)) return false;
        return linea.equals(other.linea);
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
        return "publico " + tipo.toString() + " " + linea;
    }

    @Override
    public String getCategoria() {
        return "Publico - " + tipo.toString();
    }
}
