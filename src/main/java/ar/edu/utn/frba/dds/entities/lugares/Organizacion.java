package ar.edu.utn.frba.dds.entities.lugares;

import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;
import ar.edu.utn.frba.dds.entities.personas.Contacto;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

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

    @Transient
    private UbicacionGeografica ubicacion;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    @Transient
    private ClasificacionOrganizacion clasificacionOrganizacion;

    @OneToMany(mappedBy = "organizacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Sector> sectores;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private List<Medicion> mediciones;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organizacion")
//    @JoinColumn(name = "organizacion_id", referencedColumnName = "organizacion_id")
//    @Transient
//    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, targetEntity = Contacto.class)
//    @JoinColumn(name = "organizacion_id", referencedColumnName = "organizacion_id")
    private List<ContactoMail> contactosMail;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organizacion")
//    @JoinColumn(name = "organizacion_id", referencedColumnName = "organizacion_id")
//    @Transient
//    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "organizacion_id", referencedColumnName = "organizacion_id")
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
                .flatMap(sector -> sector.getListaDeMiembros().stream())
                .collect(Collectors.toSet());
    }

    public void agregarSector(Sector sector) {
        if (this.sectores.contains(sector)) {
            throw new SectorException("El sector ya pertenece a la organización.");
        }
        sectores.add(sector);
    }

    public HashSet<Sector> getListaDeSectores() {
        return (HashSet<Sector>) this.sectores;
    }

    public void agregarMediciones(Medicion... variasMediciones) {
        Collections.addAll(this.mediciones, variasMediciones);
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

    /*public void agregarReporte(ReporteOrganizacion reporte) {
        reporte.setId(this.reportes.size()); //reporte1: id 0 - reporte2: id 1 - reporte3: id 2
        this.reportes.add(reporte);
    }*/

    @Override
    public String toString() {
        return '\n' + "Organizacion: " + "Nombre = " + razonSocial;
    }
}
