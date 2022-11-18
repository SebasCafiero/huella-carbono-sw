package ar.edu.utn.frba.dds.entities.personas;

import javax.persistence.*;

@Entity
@DiscriminatorValue("MAIL")
public class ContactoMail extends Contacto {

    @Column(name = "contraseña")
    private String password;

    public ContactoMail() {
    }

    public ContactoMail(String direccion) {
        super(direccion);
    }

    public ContactoMail(String direccion, String password) {
        super(direccion);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
