package ar.edu.utn.frba.dds.entities.personas;

import javax.persistence.Column;

public class ContactoMail extends Contacto {

    @Column(name = "contraseña")
    private String password;

    public ContactoMail() {
    }

    public ContactoMail(String direccion, String passsword) {
        super(direccion);
        this.password = passsword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
