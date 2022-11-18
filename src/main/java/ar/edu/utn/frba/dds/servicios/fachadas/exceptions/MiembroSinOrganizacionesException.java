package ar.edu.utn.frba.dds.servicios.fachadas.exceptions;

import ar.edu.utn.frba.dds.entities.personas.Miembro;

public class MiembroSinOrganizacionesException extends RuntimeException {
    private final Miembro miembro;

    public MiembroSinOrganizacionesException(Miembro miembro) {
        super();
        this.miembro = miembro;
    }

    public Miembro getMiembro() {
        return miembro;
    }
}
