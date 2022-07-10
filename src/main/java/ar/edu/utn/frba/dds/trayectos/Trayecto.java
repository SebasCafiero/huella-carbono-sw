package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Trayecto {

    private Integer id;
    private List<Tramo> tramos;
//    private LocalDate fecha;

    public Trayecto(){
        tramos = new ArrayList<>();
    }

    public Trayecto(Tramo... tramos){
        this.tramos = new ArrayList<>();
        Collections.addAll(this.tramos,tramos);
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

    public Float calcularDistanciaMedia(){
        return this.calcularDistancia()/tramos.size();
    }

    public Object getId() {
        return id;
    }

}
