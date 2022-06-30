package ar.edu.utn.frba.dds.excepciones;

public class FechaException extends Exception{
    public FechaException(){}
    public FechaException(String msjError){
        super(msjError);
    }
}
