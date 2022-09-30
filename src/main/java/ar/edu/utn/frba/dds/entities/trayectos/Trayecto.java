package ar.edu.utn.frba.dds.entities.trayectos;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "TRAYECTO")
public class Trayecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trayecto", cascade = CascadeType.ALL)
    private List<Tramo> tramos;
    @Transient
    private List<Miembro> miembros;
    @Column
    private Integer compartido;
    @Embedded
//    @Transient
    private Periodo periodo;

    public Trayecto() {
        this.miembros = new ArrayList<>();
        this.tramos = new ArrayList<>();
    }

    public Trayecto(Periodo periodo) {
        this.periodo = periodo;
        this.miembros = new ArrayList<>();
        this.tramos = new ArrayList<>();
    }

    public Trayecto(Tramo... tramos){
        this.miembros = new ArrayList<>();
        this.tramos = new ArrayList<>();
        Collections.addAll(this.tramos,tramos);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Tramo> getTramos() {
        return tramos;
    }

    public void setTramos(List<Tramo> tramos) {
        this.tramos = tramos;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public void agregarTramos(List<Tramo> tramos){
        this.tramos.addAll(tramos);
    }

    public void agregarTramo(Tramo unTramo){
        this.tramos.add(unTramo);
    }

    public Integer getCompartido() {
        return compartido;
    }

    public void setCompartido(Integer compartido) {
        this.compartido = compartido;
    }

    public Coordenada obtenerPuntoInicial(){
        return tramos.get(0).getUbicacionInicial().getCoordenada(); //PONER EXCEPCION SI LISTA VACIA O USAR OPTIONAL
//        return tramos.stream().findFirst().map(t -> t.getUbicacionInicial().getCoordenada()).orElse();
    }

    public Coordenada obtenerPuntoFinal(){
        return tramos.get(tramos.size()-1).getUbicacionFinal().getCoordenada(); //PONER EXCEPCION SI LISTA VACIA O USAR OPTIONAL
    }

    public Float calcularDistancia(){
        return tramos.stream().map(Tramo::getValor).reduce(0F, Float::sum);
    }

    public Float calcularDistanciaMedia(){
        return this.calcularDistancia()/tramos.size();
    }

    public void agregarmiembro(Miembro miembro) {
        this.miembros.add(miembro);
    }

    public Integer cantidadDeMiembros() {
        return miembros.size();
    }

    public boolean perteneceAPeriodo(Integer anio, Integer mes) {
        return periodo.incluye(anio, mes);
    }
}
