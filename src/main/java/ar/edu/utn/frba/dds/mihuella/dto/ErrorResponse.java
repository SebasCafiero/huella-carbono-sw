package ar.edu.utn.frba.dds.mihuella.dto;

import spark.ModelAndView;

public class ErrorResponse {
    private String error;
    private String descripcion;

    public ErrorResponse(String descripcion) {
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

    public ModelAndView generarVista(Object parametro) {
        return new ModelAndView(parametro, "error.hbs");
    }

    @Override
    public String toString() {
        return getError() + " - " + getDescripcion();
    }
}
