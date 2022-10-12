package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDeDocumento tipoDeDocumento;

    @Column(name = "numero_documento")
    private int nroDocumento;

    @ManyToMany(mappedBy = "miembros", fetch = FetchType.EAGER)
    private Set<Sector> sectoresDondeTrabaja; //Los sectores conocen las organizaciones

    @Transient
    private List<Trayecto> trayectos;

    public Miembro() {
        this.sectoresDondeTrabaja = new HashSet<>();
        this.trayectos = new ArrayList<>();
    }

    public Miembro(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, int nroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.nroDocumento = nroDocumento;
        this.sectoresDondeTrabaja = new HashSet<>();
        this.trayectos = new ArrayList<>();
    }

    public Set<Sector> getSectoresDondeTrabaja() {
        return this.sectoresDondeTrabaja;
    }

    public Set<Organizacion> organizacionesDondeTrabaja(){
        return this.sectoresDondeTrabaja
                .stream()
                .map(sector -> sector.getOrganizacion())
                .collect(Collectors.toSet());
    }

    public String nombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public void agregarSector(Sector sector) {
        if(this.trabajaEnSector(sector))
            throw new MiembroException("El miembro ya trabaja en ese sector");
        this.sectoresDondeTrabaja.add(sector);
    }

    public void quitarSector(Sector sector) {
        if(!this.trabajaEnSector(sector))
            throw new MiembroException("El miembro no trabaja en ese sector");
        this.sectoresDondeTrabaja.remove(sector);
    }

    public void solicitarIngresoAlSector(Sector sector) {
        sector.agregarMiembro(this);
    }

    public Integer cantidadDeSectoresDondeTrabaja() {
        return this.sectoresDondeTrabaja.size();
    }

    public Integer cantidadDeOrganizacionesDondeTrabaja() {
        return this.organizacionesDondeTrabaja().size();
    }

    public boolean trabajaEnSector(Sector unSector) {
        return this.sectoresDondeTrabaja.contains(unSector);
    }

    public void registrarTrayecto(Trayecto unTrayecto) {
        this.trayectos.add(unTrayecto);
        //registrar en cada organizacion en la que trabaja
//        this.sectoresDondeTrabaja.iterator().next().getOrganizacion().cargarTrayecto(unTrayecto,this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(int nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Trayecto getTrayecto(Integer index) {
        return trayectos.get(index - 1);
    }

    public void agregarTrayecto(Trayecto trayecto) {
        if(trayectos.contains(trayecto)) System.out.println("TRAYECTO REPETIDO EN MIEMBRO ("+nroDocumento+").");
        trayectos.add(trayecto);
    }

    public Integer cantidadTrayectos() {
        return trayectos.size();
    }

    public List<Trayecto> getTrayectos() {
        return trayectos;
    }

//    @Override
//    public String toString() {
//        return "Miembro{" +
//                "nombre='" + nombre + '\'' +
//                ", apellido='" + apellido + '\'' +
//                ", tipoDeDocumento=" + tipoDeDocumento +
//                ", nroDocumento=" + nroDocumento +
//                ", sectoresDondeTrabaja=" + sectoresDondeTrabaja +
//                ", trayectos=" + trayectos +
//                '}';
//    }
    /*TO string raro que no quisimos borrar por si era util
    @Override
    public String toString() {
        return "Miembro{<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;nombre='" + nombre + '\'' +
                "}<br>";
    }*/
}

