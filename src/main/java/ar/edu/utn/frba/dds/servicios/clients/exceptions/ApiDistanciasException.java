package ar.edu.utn.frba.dds.servicios.clients.exceptions;

public class ApiDistanciasException extends RuntimeException {
    private String atributo;
    private String valorEsperado;
    private String ioError;

    public ApiDistanciasException() {
    }

    public ApiDistanciasException(String ioError) {
        this.ioError = ioError;
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
        return ioError == null
                ? "El servicio externo no conoce " + atributo + " con valor " + valorEsperado
                : "Ha ocurrido un error de conexion con el servicio de api distancias";
    }
}
