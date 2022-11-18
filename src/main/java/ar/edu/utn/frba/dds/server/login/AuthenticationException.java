package ar.edu.utn.frba.dds.server.login;

public class AuthenticationException extends RuntimeException {
    private int codigo;

    public AuthenticationException() {
        this.codigo = 401;
    }

    public String getMessage() {
        return "Error de autenticación. No existe la sesión indicada";
    }
}
