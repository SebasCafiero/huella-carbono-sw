package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "miembro_id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDeDocumento tipoDeDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private int nroDocumento;

    @ManyToMany(mappedBy = "miembros", fetch = FetchType.LAZY)
    private Set<Sector> sectores;

    @ManyToMany(mappedBy = "miembros")
    private List<Trayecto> trayectos;

    public Miembro() {
        this.sectores = new HashSet<>();
        this.trayectos = new ArrayList<>();
    }

    public Miembro(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, int nroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.nroDocumento = nroDocumento;
        this.sectores = new HashSet<>();
        this.trayectos = new ArrayList<>();
    }

    public Set<Sector> getSectores() {
        return this.sectores;
    }

    public Set<Organizacion> getOrganizaciones(){
        return this.sectores
                .stream()
                .map(Sector::getOrganizacion)
                .collect(Collectors.toSet());
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public void agregarSector(Sector sector) {
        if(this.trabajaEnSector(sector))
            throw new MiembroException("El miembro ya trabaja en ese sector");
        this.sectores.add(sector);
    }

    public void quitarSector(Sector sector) {
        if(!this.trabajaEnSector(sector))
            throw new MiembroException("El miembro no trabaja en ese sector");
        this.sectores.remove(sector);
    }

    public void solicitarIngresoAlSector(Sector sector) {
        sector.agregarMiembro(this);
    }

    public Integer cantidadDeSectoresDondeTrabaja() {
        return this.getSectores().size();
    }

    public Integer cantidadDeOrganizacionesDondeTrabaja() {
        return this.getOrganizaciones().size();
    }

    public boolean trabajaEnSector(Sector unSector) {
        return this.sectores.contains(unSector);
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
        trayectos.add(trayecto);
    }

    public void quitarTrayecto(Trayecto trayecto) {
        this.trayectos.remove(trayecto);
    }

    public Integer cantidadTrayectos() {
        return trayectos.size();
    }

    public List<Trayecto> getTrayectos() {
        return trayectos;
    }

    @Override
    public String toString() {
        return "Miembro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}

