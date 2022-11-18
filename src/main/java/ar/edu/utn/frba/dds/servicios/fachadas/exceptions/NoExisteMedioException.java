package ar.edu.utn.frba.dds.servicios.fachadas.exceptions;

public class NoExisteMedioException extends RuntimeException {
    private final String tipo;
    private final String discriminante1;
    private final String discriminante2;

    public NoExisteMedioException(String tipo) {
        super();
        this.tipo = tipo;
        this.discriminante1 = "";
        this.discriminante2 = "";
    }

    public NoExisteMedioException(String tipo, String discriminante1) {
        super();
        this.tipo = tipo;
        this.discriminante1 = discriminante1;
        this.discriminante2 = "";
    }

    public NoExisteMedioException(String tipo, String discriminante1, String discriminante2) {
        super();
        this.tipo = tipo;
        this.discriminante1 = discriminante1;
        this.discriminante2 = discriminante2;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDiscriminante1() {
        return discriminante1;
    }

    public String getDiscriminante2() {
        return discriminante2;
    }

    @Override
    public String getMessage() {
        return "El medio de transporte " + this.tipo + " " + this.discriminante1 + " " + this.discriminante2 + " no existe";
    }

}
