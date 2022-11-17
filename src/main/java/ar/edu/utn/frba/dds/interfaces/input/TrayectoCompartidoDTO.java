package ar.edu.utn.frba.dds.interfaces.input;

public class TrayectoCompartidoDTO {
    private String tipoDocumento;
    private Integer miembroDNI;
    private Integer trayectoReferencia;

    public TrayectoCompartidoDTO() {
    }

    public TrayectoCompartidoDTO(Integer miembroDNI, Integer trayectoReferencia) {
        this.miembroDNI = miembroDNI;
        this.trayectoReferencia = trayectoReferencia;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getMiembroDNI() {
        return miembroDNI;
    }

    public void setMiembroDNI(Integer miembroDNI) {
        this.miembroDNI = miembroDNI;
    }

    public Integer getTrayectoReferencia() {
        return trayectoReferencia;
    }

    public void setTrayectoReferencia(Integer trayectoReferencia) {
        this.trayectoReferencia = trayectoReferencia;
    }
}
