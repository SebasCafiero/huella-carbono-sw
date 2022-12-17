package ar.edu.utn.frba.dds.interfaces.input.json;

import ar.edu.utn.frba.dds.server.login.User;

public class NuevaEntidadGenericaRequest<T> {
    private User usuario;
    private T entidad;

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public T getEntidad() {
        return entidad;
    }

    public void setEntidad(T entidad) {
        this.entidad = entidad;
    }
}
