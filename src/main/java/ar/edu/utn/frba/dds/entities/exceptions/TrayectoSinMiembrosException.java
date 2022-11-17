package ar.edu.utn.frba.dds.entities.exceptions;

import ar.edu.utn.frba.dds.entities.medibles.Trayecto;

public class TrayectoSinMiembrosException extends RuntimeException {
    private Trayecto trayecto;

    public TrayectoSinMiembrosException(Trayecto trayecto) {
        super();
        this.trayecto = trayecto;
    }

    public Trayecto getTrayecto() {
        return trayecto;
    }
}
