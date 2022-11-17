package ar.edu.utn.frba.dds.interfaces.input.json;

public class AgenteSectorialJSONDTO {
    private int area;
    private ContactoMailJSONDTO contactoMail;
    private String telefono;

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
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
