package ar.edu.utn.frba.dds.interfaces.input;

public class ErrorDTO {
    private String error;
    private String descripcion;

    public ErrorDTO() {
    }

    public ErrorDTO(String descripcion) {
        this.error = "Solicitud inválida";
        this.descripcion = descripcion;
    }

    public ErrorDTO(String error, String descripcion) {
        this.error = error;
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
