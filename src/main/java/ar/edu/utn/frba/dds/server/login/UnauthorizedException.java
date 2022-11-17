package ar.edu.utn.frba.dds.server.login;

public class UnauthorizedException extends RuntimeException {
    private int codigo;

    public UnauthorizedException() {
        this.codigo = 401;
    }

    public String getMessage() {
        return codigo + " Unauthorized";
    }
}
