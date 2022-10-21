package ar.edu.utn.frba.dds.entities.personas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//@Entity
//@DiscriminatorValue("TELEFONO")
public class ContactoTelefono extends Contacto {

    public ContactoTelefono() {
    }

    public ContactoTelefono(String direccion) {
        super(direccion);
    }

}
