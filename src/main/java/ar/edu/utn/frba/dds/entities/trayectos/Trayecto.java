package ar.edu.utn.frba.dds.entities.trayectos;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import javax.persistence.*;
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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "MIEMBRO_POR_TRAYECTO",
            joinColumns = { @JoinColumn(name = "trayectp_id") },
            inverseJoinColumns = { @JoinColumn(name = "miembro_id") }
    )
    private List<Miembro> miembros;

    @Column
    private Integer compartido;

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

    public void agregarTramos(List<Tramo> tramos){
        this.tramos.addAll(tramos);
        tramos.forEach(tr -> tr.setTrayecto(this)); //todo
    }

    public void agregarTramo(Tramo unTramo){
        this.tramos.add(unTramo);
        unTramo.setTrayecto(this); //todo
    }

    public Integer getCompartido() {
        return compartido;
    }

    public void setCompartido(Integer compartido) {
        this.compartido = compartido;
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
        this.miembros.add(miembro);
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
                ", compartido=" + compartido +
                ", periodo=" + periodo +
                '}';
    }
}
