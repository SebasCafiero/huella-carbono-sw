package ar.edu.utn.frba.dds.mihuella.dto;

public class TrayectoCompartidoDTO {
    private Integer miembroDNI;
    private Integer trayectoReferencia;

    public TrayectoCompartidoDTO() {
    }

    public TrayectoCompartidoDTO(Integer miembroDNI, Integer trayectoReferencia) {
        this.miembroDNI = miembroDNI;
        this.trayectoReferencia = trayectoReferencia;
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
