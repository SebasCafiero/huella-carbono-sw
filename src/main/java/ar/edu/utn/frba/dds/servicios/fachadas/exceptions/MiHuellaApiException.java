package ar.edu.utn.frba.dds.servicios.fachadas.exceptions;

import ar.edu.utn.frba.dds.interfaces.input.ErrorDTO;

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
