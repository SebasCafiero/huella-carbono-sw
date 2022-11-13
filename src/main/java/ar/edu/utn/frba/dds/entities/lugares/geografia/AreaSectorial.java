package ar.edu.utn.frba.dds.entities.lugares.geografia;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AreaSectorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "area_id")
    private Integer id;

    @Column(name = "nombre")
    protected String nombre;

    @Transient
    protected Set<Organizacion> organizaciones;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id", referencedColumnName = "agente_id")
    protected AgenteSectorial agente;

    @Column(name = "id_api_distancias")
    private Integer idApiDistancias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Organizacion> getOrganizaciones() {
        return this.organizaciones;
    }

    public void agregarOrganizacion(Organizacion unaOrganizacion) {
        this.organizaciones.add(unaOrganizacion);
    }

    public Set<UbicacionGeografica> getUbicaciones() {
        return this.organizaciones.stream()
                .map(Organizacion::getUbicacion).collect(Collectors.toSet());
    }

    public AgenteSectorial getAgente() {
        return agente;
    }

    public void setAgente(AgenteSectorial agente) {
        this.agente = agente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOrganizaciones(Set<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }

    public Integer getIdApiDistancias() {
        return idApiDistancias;
    }

    public void setIdApiDistancias(Integer idApiDistancias) {
        this.idApiDistancias = idApiDistancias;
    }
}
