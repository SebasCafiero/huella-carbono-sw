package ar.edu.utn.frba.dds.login;

public class ForbiddenException extends Throwable{

    private int codigo;

    public ForbiddenException() {
        this.codigo = 403;
    }

    public String getMessage() {
        return codigo + " Forbidden";
    }
}
