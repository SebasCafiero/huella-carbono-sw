package ar.edu.utn.frba.dds.mihuella;

public class MedicionSinFactorEmisionException extends Throwable {

    private String categoria;

    public MedicionSinFactorEmisionException(String categoria) {
        super();
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}
