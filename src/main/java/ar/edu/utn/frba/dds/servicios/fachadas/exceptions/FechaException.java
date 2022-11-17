package ar.edu.utn.frba.dds.servicios.fachadas.exceptions;

public class FechaException extends RuntimeException {
    public FechaException() {}

    public FechaException(String msjError){
        super(msjError);
    }
}
