package ar.edu.utn.frba.dds.login;

public class UnauthorizedException extends Throwable{

    private int codigo;

    public UnauthorizedException() {
        this.codigo = 401;
    }

    public String getMessage() {
        return codigo + " Unauthorized";
    }
}
