package ar.edu.utn.frba.dds.controllers;

public class ErrorDTO {
    private String error;
    private String descripcion;

    public ErrorDTO() {
    }

    public ErrorDTO(String descripcion) {
        this.error = "Solicitud inválida";
        this.descripcion = descripcion;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
