package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    Organizacion organizacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    Miembro miembro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    AgenteSectorial agenteSectorial;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Organizacion organizacion, Miembro miembro, AgenteSectorial agenteSectorial) {
        this.username = username;
        this.password = password;
        this.organizacion = organizacion;
        this.miembro = miembro;
        this.agenteSectorial = agenteSectorial;
    }

    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public AgenteSectorial getAgenteSectorial() {
        return agenteSectorial;
    }

    public void setAgenteSectorial(AgenteSectorial agenteSectorial) {
        this.agenteSectorial = agenteSectorial;
    }

    public String getRol() {
        if (miembro != null){
            return "miembro";
        }
        else if (agenteSectorial != null){
            return "agenteSectorial";
        }
        else if (organizacion != null){
            return "organizacion";
        }
        else
            return "admin";
    }
}
