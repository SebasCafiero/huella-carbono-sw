package ar.edu.utn.frba.dds.entities.personas;

public class MiembroException extends Exception{
    public MiembroException(){}

    public MiembroException(String msjError){
        super(msjError);
    }
}
