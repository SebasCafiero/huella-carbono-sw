package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.personas.Miembro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trayecto {

    private Integer id;
    private List<Tramo> tramos;
    private List<Miembro> miembros;
    private LocalDate fecha;
    private Character periodicidad;

    public Trayecto(LocalDate fecha, Character periodicidad) {
        this.fecha = fecha;
        this.periodicidad = periodicidad;
        this.miembros = new ArrayList<>();
        this.tramos = new ArrayList<>();
    }

    public Trayecto(Tramo... tramos){
        this.miembros = new ArrayList<>();
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
        return tramos.get(0).getUbicacionInicial().getCoordenada(); //PONER EXCEPCION SI LISTA VACIA O USAR OPTIONAL
//        return tramos.stream().findFirst().map(t -> t.getUbicacionInicial().getCoordenada()).orElse();
    }

    public Coordenada obtenerPuntoFinal(){
        return tramos.get(tramos.size()-1).getUbicacionFinal().getCoordenada(); //PONER EXCEPCION SI LISTA VACIA O USAR OPTIONAL
    }

    public Float calcularDistancia(){
        return tramos.stream().map(tramo->tramo.getValor()).reduce(0F,(tot,dist)->tot+dist);
    }

    public Float calcularDistanciaMedia(){
        return this.calcularDistancia()/tramos.size();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Miembro> getMiembros() {
        return this.miembros;
    }

    public void agregarmiembro(Miembro miembro) {
        this.miembros.add(miembro);
    }

    public Integer cantidadDeMiembros() {
        return miembros.size();
    }

    public Character getPeriodicidad(){
        return periodicidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getMes(){
        return fecha.getMonthValue();
    }

    public Integer getAnio() {
        return fecha.getYear();
    }
}
