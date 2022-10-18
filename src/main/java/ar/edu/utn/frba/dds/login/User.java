package ar.edu.utn.frba.dds.login;

import javax.persistence.*;

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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
}
