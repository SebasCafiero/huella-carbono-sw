package ar.edu.utn.frba.dds.mihuella.dto;

import ar.edu.utn.frba.dds.entities.personas.ContactoTelefono;

import java.util.List;

public class AgenteSectorialJSONDTO {
    private AreaSectorialJSONDTO area;
    private ContactoMailJSONDTO contactoMail;
    private String telefono;

    public AreaSectorialJSONDTO getArea() {
        return area;
    }

    public void setArea(AreaSectorialJSONDTO area) {
        this.area = area;
    }

    public ContactoMailJSONDTO getContactoMail() {
        return contactoMail;
    }

    public void setContactoMail(ContactoMailJSONDTO contactoMail) {
        this.contactoMail = contactoMail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
