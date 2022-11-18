package ar.edu.utn.frba.dds.server.login;

public class ForbiddenException extends RuntimeException {

    private int codigo;

    public ForbiddenException() {
        this.codigo = 403;
    }

    public String getMessage() {
        return codigo + " Forbidden";
    }
}
