package ar.edu.utn.frba.dds.interfaces.input.json;

public class ContactoMailJSONDTO {
    private String direccionesEMail;
    private String password;

    public String getDireccionesEMail() {
        return direccionesEMail;
    }

    public void setDireccionesEMail(String direccionesEMail) {
        this.direccionesEMail = direccionesEMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
