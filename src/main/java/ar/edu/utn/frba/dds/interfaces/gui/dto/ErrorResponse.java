package ar.edu.utn.frba.dds.interfaces.gui.dto;

import spark.ModelAndView;

import java.util.HashMap;

public class ErrorResponse {
    private String error;
    private String descripcion;

    public ErrorResponse(String descripcion) {
        this.error = "Solicitud inválida";
        this.descripcion = descripcion;
    }

    public ErrorResponse() {
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

    public ModelAndView generarVista(String msg) {
        HashMap<String, Object> parametro = new HashMap<>();
        parametro.put("descripcion", msg);
        return new ModelAndView(parametro, "error.hbs");
    }

    @Override
    public String toString() {
        return getError() + " - " + getDescripcion();
    }
}
