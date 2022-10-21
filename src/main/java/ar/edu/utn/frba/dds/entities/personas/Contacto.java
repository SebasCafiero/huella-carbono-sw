package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

import javax.persistence.*;

@Entity
@Table(name = "CONTACTO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERICO")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contacto_id")
    private Integer id;

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "agente_id")
    private AgenteSectorial agente;

    public Contacto() {
    }

    public Contacto(String direccion) {
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public AgenteSectorial getAgente() {
        return agente;
    }

    public void setAgente(AgenteSectorial agente) {
        this.agente = agente;
    }
}
