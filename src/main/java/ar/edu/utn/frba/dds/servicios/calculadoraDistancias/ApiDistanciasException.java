package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

public class ApiDistanciasException extends RuntimeException {
    private String atributo;
    private String valorEsperado;

    public ApiDistanciasException() {
    }

    public ApiDistanciasException(String atributo, String valorEsperado) {
        this.valorEsperado = valorEsperado;
        this.atributo = atributo;
    }

    public String getValorEsperado() {
        return valorEsperado;
    }

    public String getAtributo() {
        return atributo;
    }

    @Override
    public String getMessage() {
        return "El servicio externo no conoce " + atributo + " con valor " + valorEsperado;
    }
}
