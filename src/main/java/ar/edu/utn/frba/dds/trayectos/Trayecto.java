package ar.edu.utn.frba.dds.trayectos;

import java.util.ArrayList;
import java.util.List;

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

    public Float calcularDistancia(){
        return tramos.stream().map(tramo->tramo.calcularDistancia()).reduce(0F,(tot,dist)->tot+dist);
    }
}
