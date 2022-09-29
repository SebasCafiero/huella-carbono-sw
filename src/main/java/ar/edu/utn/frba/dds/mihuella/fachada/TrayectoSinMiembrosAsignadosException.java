package ar.edu.utn.frba.dds.mihuella.fachada;

import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

public class TrayectoSinMiembrosAsignadosException extends RuntimeException {
    private Trayecto trayecto;

    public TrayectoSinMiembrosAsignadosException(Trayecto trayecto) {
        super();
        this.trayecto = trayecto;
    }

    public Trayecto getTrayecto() {
        return trayecto;
    }
}
