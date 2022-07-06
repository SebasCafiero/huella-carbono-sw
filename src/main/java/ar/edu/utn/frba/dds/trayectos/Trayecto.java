package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trayecto implements Medible {

    private Integer id;
    private List<Tramo> tramos;

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

    public Object getId() {
        return id;
    }

    @Override
    public String getUnidad() {
        return null;
        // TODO
    }

    @Override
    public Float getValor() {
        return null;
        // TODO
    }

    @Override
    public String getCategoria() {
        return null;
        // TODO
    }
}
