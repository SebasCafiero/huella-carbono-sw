package ar.edu.utn.frba.dds.excepciones;

public class MiembroException extends Exception{
    public MiembroException(){}

    public MiembroException(String msjError){
        super(msjError);
    }
}
