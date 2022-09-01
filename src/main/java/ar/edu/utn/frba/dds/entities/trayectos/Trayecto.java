package ar.edu.utn.frba.dds.entities.trayectos;

import ar.edu.utn.frba.dds.entities.EntidadPersistente;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trayecto extends EntidadPersistente {
    private List<Tramo> tramos;
    private List<Miembro> miembros;
    private Periodo fecha;

    /*public Trayecto(LocalDate fecha, Character periodicidad) {
        this.fecha = fecha;
        this.periodicidad = periodicidad;
        this.miembros = new ArrayList<>();
        this.tramos = new ArrayList<>();
    }*/

    public Trayecto(Periodo fecha) {
        this.fecha = fecha;
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
        return tramos.stream().map(tramo -> tramo.getValor()).reduce(0F, (tot, dist) -> tot + dist);
    }

    public Float calcularDistanciaMedia(){
        return this.calcularDistancia()/tramos.size();
    }

    public void setTramos(List<Tramo> tramos) {
        this.tramos = tramos;
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
        return fecha.getPeriodicidad();
    }

    /*public LocalDate getFecha() {
        return fecha;
    }

    public Integer getMes(){
        return fecha.getMonthValue();
    }

    public Integer getAnio() {
        return fecha.getYear();
    }*/

    public Periodo getFecha() {
        return fecha;
    }

    public Integer getMes() {
        return fecha.getMes();
    }

    public Integer getAnio() {
        return fecha.getAnio();
    }

    public boolean perteneceAPeriodo(Integer anio, Integer mes) {
        if(getPeriodicidad().equals('A')) {
            return getAnio().equals(anio);
        } else if(getPeriodicidad().equals('M')) {
            return getAnio().equals(anio) && getMes().equals(mes);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Trayecto{" +
                "tramos=" + tramos +
                ", fecha=" + fecha +
                "} " + super.toString();
    }
}