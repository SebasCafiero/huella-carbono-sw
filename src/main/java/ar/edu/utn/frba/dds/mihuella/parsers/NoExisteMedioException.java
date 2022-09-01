package ar.edu.utn.frba.dds.mihuella.parsers;

public class NoExisteMedioException extends RuntimeException {

    private final String tipo;
    private final String atr1;
    private final String atr2;

    public NoExisteMedioException(String tipo, String atr1, String atr2) {
        super();
        this.tipo = tipo;
        this.atr1 = atr1;
        this.atr2 = atr2;
    }

    @Override
    public String getMessage() {
        return "El medio de transporte " + this.tipo + " con los atributos " + this.atr1 + " y " + this.atr2 + " no existe";
    }

    public String getTipo() {
        return tipo;
    }

    public String getAtr1() {
        return atr1;
    }

    public String getAtr2() {
        return atr2;
    }
}
