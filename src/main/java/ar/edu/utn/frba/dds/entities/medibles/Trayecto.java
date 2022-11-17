package ar.edu.utn.frba.dds.entities.medibles;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "MIEMBRO_POR_TRAYECTO",
            joinColumns = { @JoinColumn(name = "trayecto_id") },
            inverseJoinColumns = { @JoinColumn(name = "miembro_id") }
    )
    private List<Miembro> miembros;

    @Embedded
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

    public Trayecto(Periodo periodo, Tramo... tramos) {
        this.periodo = periodo;
        this.miembros = new ArrayList<>();
        this.tramos = new ArrayList<>();
        Collections.addAll(this.tramos, tramos);
        Arrays.asList(tramos).forEach(tr -> tr.setTrayecto(this));
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
        tramos.forEach(tr -> tr.setTrayecto(this)); //todo
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

    public void agregarTramos(List<Tramo> tramos) {
        this.tramos.addAll(tramos);
        tramos.forEach(tr -> tr.setTrayecto(this));
    }

    public void agregarTramo(Tramo unTramo) {
        this.tramos.add(unTramo);
        unTramo.setTrayecto(this);
    }

    public Coordenada obtenerPuntoInicial(){
        return tramos.get(0).getUbicacionInicial().getCoordenada();
    }

    public Coordenada obtenerPuntoFinal(){
        return tramos.get(tramos.size()-1).getUbicacionFinal().getCoordenada();
    }

    public Float calcularDistancia(){
        return tramos.stream().map(Tramo::getValor).reduce(0F, Float::sum);
    }

    public Float calcularDistanciaMedia(){
        return this.calcularDistancia()/tramos.size();
    }

    public void agregarMiembro(Miembro miembro) {
        if(!miembros.contains(miembro))
            this.miembros.add(miembro);
    }

    public void quitarMiembro(Miembro miembro) {
        this.miembros.remove(miembro);
    }

    public Integer cantidadDeMiembros() {
        return miembros.size();
    }

    public boolean perteneceAPeriodo(Integer anio, Integer mes) {
        return periodo.incluye(anio, mes);
    }

    @Override
    public String toString() {
        return "\nTrayecto{" +
                "id=" + id +
                ", tramos=" + tramos +
                ", miembros=" + miembros +
                ", periodo=" + periodo +
                '}';
    }
}
