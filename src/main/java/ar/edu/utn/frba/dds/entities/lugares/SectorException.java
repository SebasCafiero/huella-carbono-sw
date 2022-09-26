package ar.edu.utn.frba.dds.entities.lugares;

public class SectorException extends RuntimeException {

    public SectorException() {}

    public SectorException(String msjError){
        super(msjError);
    }
}
