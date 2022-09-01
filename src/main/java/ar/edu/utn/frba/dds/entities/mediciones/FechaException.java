package ar.edu.utn.frba.dds.entities.mediciones;

public class FechaException extends RuntimeException {
    public FechaException() {}

    public FechaException(String msjError){
        super(msjError);
    }
}
