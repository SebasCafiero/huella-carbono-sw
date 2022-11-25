package ar.edu.utn.frba.dds.servicios.fachadas.exceptions;

public class ReporteException extends RuntimeException {
    public ReporteException(String desc) {
        System.out.println(desc);
        printStackTrace();
//        super(desc);
    }
}
