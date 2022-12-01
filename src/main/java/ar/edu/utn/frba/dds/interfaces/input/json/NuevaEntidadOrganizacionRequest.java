package ar.edu.utn.frba.dds.interfaces.input.json;

import ar.edu.utn.frba.dds.server.login.User;

public class NuevaEntidadOrganizacionRequest {
    private User usuario;
    private OrganizacionJSONDTO entidad;

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public OrganizacionJSONDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(OrganizacionJSONDTO entidad) {
        this.entidad = entidad;
    }
}
