package ar.edu.utn.frba.dds.controllers;

public class MiHuellaApiException extends RuntimeException {
    private final ErrorDTO error;

    public MiHuellaApiException(String descripcion) {
        super(descripcion);
        this.error = new ErrorDTO(descripcion);
    }

    public MiHuellaApiException(ErrorDTO error) {
        super();
        this.error = error;
    }

    public ErrorDTO getError() {
        return error;
    }
}
