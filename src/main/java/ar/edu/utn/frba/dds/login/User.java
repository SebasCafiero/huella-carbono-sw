package ar.edu.utn.frba.dds.login;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column (name = "username")
    String username;
    @Column (name = "password")
    String password;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Organizacion> organizaciones;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<Organizacion> organizaciones) {
        this.username = username;
        this.password = password;
        this.organizaciones = organizaciones;
    }

    public User() {}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public List<Organizacion> getOrganizaciones() {return organizaciones;}

    public void setOrganizaciones(List<Organizacion> organizaciones) {this.organizaciones = organizaciones;}
}
