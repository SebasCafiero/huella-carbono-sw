package ar.edu.utn.frba.dds.mihuella.fachada;

public class MedicionSinFactorEmisionException extends RuntimeException {

    private final String categoria;

    public MedicionSinFactorEmisionException(String categoria) {
        super();
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String getMessage() {
        return "No hay factor de emision para la categoria " + categoria;
    }
}
