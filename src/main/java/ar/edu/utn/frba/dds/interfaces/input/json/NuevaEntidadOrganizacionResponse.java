package ar.edu.utn.frba.dds.interfaces.input.json;

import ar.edu.utn.frba.dds.interfaces.input.ErrorDTO;

public class NuevaEntidadOrganizacionResponse {
    private String estado;
    private ErrorDTO error;
    private Integer usuario;
    private Integer organizacion;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Integer organizacion) {
        this.organizacion = organizacion;
    }
}
