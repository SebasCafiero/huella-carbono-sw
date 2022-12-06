package ar.edu.utn.frba.dds.servicios.clients.exceptions;

import ar.edu.utn.frba.dds.entities.lugares.Direccion;

public class ApiGeocodingException extends RuntimeException {
    private Direccion direccion;

    public ApiGeocodingException() {
    }

    public ApiGeocodingException(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String getMessage() {
        return "El servicio geocoding no pudo encontrar la dirección solicitada: " +
                direccion.toString();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
