package ar.edu.utn.frba.dds.server.login;

public class NotLoggedException extends RuntimeException {

    public NotLoggedException() {
    }

    public String getMessage() {
        return "Debe tener sesión para acceder a esta dirección";
    }
}
