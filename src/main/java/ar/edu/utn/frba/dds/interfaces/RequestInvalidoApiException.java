package ar.edu.utn.frba.dds.interfaces;

public class RequestInvalidoApiException extends RuntimeException {
    public RequestInvalidoApiException() {
    }

    public RequestInvalidoApiException(String message) {
        super(message);
    }
}
