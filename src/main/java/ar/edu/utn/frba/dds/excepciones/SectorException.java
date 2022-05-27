package ar.edu.utn.frba.dds.excepciones;

public class SectorException extends Exception{

    public SectorException(){}

    public SectorException(String msjError){
        super(msjError);
    }
}
