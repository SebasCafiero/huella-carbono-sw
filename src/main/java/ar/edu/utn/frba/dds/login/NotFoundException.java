package ar.edu.utn.frba.dds.login;

public class NotFoundException extends Throwable{
    private int codigo;

    public NotFoundException() {
        this.codigo = 404;
    }

    public String getMessage() {
        return codigo + " Not Found";
    }
}
