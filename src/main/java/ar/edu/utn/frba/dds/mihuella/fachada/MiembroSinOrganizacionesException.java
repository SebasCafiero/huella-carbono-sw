package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.personas.Miembro;

public class MiembroSinOrganizacionesException extends RuntimeException {
    private Miembro miembro;

    public MiembroSinOrganizacionesException(Miembro miembro) {
        super();
        this.miembro = miembro;
    }

    public Miembro getMiembro() {
        return miembro;
    }
}
