package ar.edu.utn.frba.dds.servicios.fachadas.exceptions;

public class NoExisteUbicacionException extends RuntimeException {

    public NoExisteUbicacionException() {
        super();
    }

    public NoExisteUbicacionException(String error) {
        super(error);
    }
}
