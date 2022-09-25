package ar.edu.utn.frba.dds.mihuella.fachada;

public class NoExisteTrayectoCompartidoException extends RuntimeException {
    private final Integer trayecto;

    public NoExisteTrayectoCompartidoException(Integer trayectoReferencia) {
        super();
        this.trayecto = trayectoReferencia;
    }

    public Integer getTrayecto() {
        return trayecto;
    }
}
