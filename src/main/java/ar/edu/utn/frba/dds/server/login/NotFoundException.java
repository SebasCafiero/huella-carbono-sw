package ar.edu.utn.frba.dds.server.login;

public class NotFoundException extends RuntimeException {
    private int codigo;

    public NotFoundException() {
        this.codigo = 404;
    }

    public String getMessage() {
        return codigo + " Not Found";
    }
}
