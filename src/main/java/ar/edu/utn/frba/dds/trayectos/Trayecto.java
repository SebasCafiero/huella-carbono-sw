package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Trayecto {
    private List<Tramo> tramos;

    public Trayecto(){
        tramos = new ArrayList<>();
    }

    public void agregarTramos(List<Tramo> tramos){
        this.tramos.addAll(tramos);
    }

    public void agregarTramo(Tramo unTramo){
        this.tramos.add(unTramo);
    }

    public List<Tramo> getTramos() {
        return tramos;
    }

    public Coordenada obtenerPuntoInicial(){
        return tramos.get(0).getCoordenadaInicial(); //PONER EXCEPCION SI LISTA VACIA
    }

    public Coordenada obtenerPuntoFinal(){
        return tramos.get(tramos.size()-1).getCoordenadaFinal(); //PONER EXCEPCION SI LISTA VACIA
    }

    public Float calcularDistancia(){
        return tramos.stream().map(tramo->tramo.calcularDistancia()).reduce(0F,(tot,dist)->tot+dist);
    }

    public Boolean tramosCompartidos( Trayecto trayecto ){

        Stream<Tramo> tramosActuales = (Stream) tramos;
        Stream<Tramo> otrosTramos = (Stream) trayecto.getTramos();

        return  tramosActuales.anyMatch(t1 -> otrosTramos.anyMatch( t2 -> t2.equals(t1)));

    }
}
