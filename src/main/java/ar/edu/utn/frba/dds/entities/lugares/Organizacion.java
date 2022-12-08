package ar.edu.utn.frba.dds.entities.lugares;

import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.medibles.Medicion;
import ar.edu.utn.frba.dds.entities.medibles.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Contacto;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ORGANIZACION")
public class Organizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "organizacion_id")
    private Integer id;

    @Column (name = "razon_social")
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    private TipoDeOrganizacionEnum tipo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "ubicacion_id")
    private UbicacionGeografica ubicacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private ClasificacionOrganizacion clasificacionOrganizacion;

    @OneToMany(mappedBy = "organizacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sector> sectores;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private List<Medicion> mediciones;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, targetEntity = Contacto.class)
    private List<ContactoMail> contactosMail;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, targetEntity = Contacto.class)
    private List<ContactoTelefono> contactosTelefono;

    @Transient
    private List<ReporteOrganizacion> reportes;

    public Organizacion() {
        this.sectores = new HashSet<>();
        this.mediciones = new ArrayList<>();
        this.reportes = new ArrayList<>();
        this.contactosTelefono = new ArrayList<>();
        this.contactosMail = new ArrayList<>();
    }

    public Organizacion(String razonSocial,
                        TipoDeOrganizacionEnum tipo,
                        ClasificacionOrganizacion clasificacionOrganizacion,
                        UbicacionGeografica ubicacion) {
        this.razonSocial = razonSocial;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.clasificacionOrganizacion = clasificacionOrganizacion;
        this.sectores = new HashSet<>();
        this.mediciones = new ArrayList<>();
        this.contactosMail = new ArrayList<>();
        this.contactosTelefono = new ArrayList<>();
        this.reportes = new ArrayList<>();
        ubicacion.getDireccion().getMunicipio().agregarOrganizacion(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Miembro> getMiembros() {
        //Hay que traer cada miembro de cada sector y que no hayan repetidos.
        return sectores
                .stream()
                .flatMap(sector -> sector.getMiembros().stream())
                .collect(Collectors.toSet());
    }

    public void agregarSector(Sector sector) {
        if (this.sectores.contains(sector))
            throw new SectorException("El sector ya pertenece a la organización");
        sectores.add(sector);
    }

    public void quitarSector(Sector sector) {
        if (!this.sectores.contains(sector))
            throw new SectorException("El sector no pertenece a la organización");


        if (!sector.getMiembros().isEmpty()) {
            throw new SectorException("No puede eliminarse un sector que tiene empleados");
        }

        this.sectores.remove(sector);
    }

    public HashSet<Sector> getListaDeSectores() {
        return (HashSet<Sector>) this.sectores;
    }

    public void agregarMediciones(Medicion... variasMediciones) {
        Collections.addAll(this.mediciones, variasMediciones);
    }

    public void agregarMediciones(List<Medicion> mediciones) {
        this.mediciones.addAll(mediciones);
    }

    public List<Medicion> getMediciones() {
        return this.mediciones;
    }

    public Integer cantidadSectores() {
        return this.sectores.size();
    }

    public Float obtenerHC(LocalDate fechaInicial, LocalDate fechaFinal) {
        return 0.0f;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public TipoDeOrganizacionEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeOrganizacionEnum tipo) {this.tipo = tipo;}

    public void setClasificacionOrganizacion(ClasificacionOrganizacion clasificacionOrganizacion) {this.clasificacionOrganizacion = clasificacionOrganizacion;}

    public void setUbicacion(UbicacionGeografica ubicacion) {this.ubicacion = ubicacion;}

    public UbicacionGeografica getUbicacion() {
        return ubicacion;
    }

    public ClasificacionOrganizacion getClasificacionOrganizacion() {
        return clasificacionOrganizacion;
    }

    public Set<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(Set<Sector> sectores) {this.sectores = sectores;}

    public List<ContactoMail> getContactosMail() {
        return contactosMail;
    }

    public List<ContactoTelefono> getContactosTelefono() {
        return contactosTelefono;
    }

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public void setContactosMail(List<ContactoMail> contactosMail) {
        this.contactosMail = contactosMail;
    }

    public void setContactosTelefono(List<ContactoTelefono> contactosTelefono) {
        this.contactosTelefono = contactosTelefono;
    }

    public void setReportes(List<ReporteOrganizacion> reportes) {
        this.reportes = reportes;
    }

    public void agregarContactoMail(ContactoMail contacto) {
        this.contactosMail.add(contacto);
        contacto.setOrganizacion(this);
    }

    public void agregarContactoTelefono(ContactoTelefono contacto) {
        this.contactosTelefono.add(contacto);
        contacto.setOrganizacion(this);
    }

    public List<Trayecto> trayectosDeMiembros() {
        return getMiembros().stream().flatMap(m -> m.getTrayectos().stream()).collect(Collectors.toList());
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public List<ReporteOrganizacion> getReportes() {
        return reportes;
    }

}
