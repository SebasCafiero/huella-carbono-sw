package ar.edu.utn.frba.dds.entities.exceptions;

public class MiembroException extends RuntimeException {
    public MiembroException() {}

    public MiembroException(String msjError){
        super(msjError);
    }
}
