package ar.edu.utn.frba.dds.entities.exceptions;

public class SectorException extends RuntimeException {

    public SectorException() {}

    public SectorException(String msjError){
        super(msjError);
    }
}
