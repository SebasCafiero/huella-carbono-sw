package ar.edu.utn.frba.dds.personas;

public class ContactoMail {
    private String direccionesEMail;
    private String passsword;

    public ContactoMail(String direccionesEMail, String passsword) {
        this.direccionesEMail = direccionesEMail;
        this.passsword = passsword;
    }

    public String getDireccionEMail() {
        return direccionesEMail;
    }

    public void setDireccionEMail(String direccionesEMail) {
        this.direccionesEMail = direccionesEMail;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }
}
