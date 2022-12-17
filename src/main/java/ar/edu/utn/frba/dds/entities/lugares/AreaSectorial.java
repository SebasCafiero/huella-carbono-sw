package ar.edu.utn.frba.dds.entities.lugares;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = true)
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

    public abstract Set<Organizacion> getOrganizaciones();

    public Set<UbicacionGeografica> getUbicaciones() {
        return getOrganizaciones().stream()
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

    public Integer getIdApiDistancias() {
        return idApiDistancias;
    }

    public void setIdApiDistancias(Integer idApiDistancias) {
        this.idApiDistancias = idApiDistancias;
    }
}
