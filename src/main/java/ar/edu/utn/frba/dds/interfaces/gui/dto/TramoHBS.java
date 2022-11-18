package ar.edu.utn.frba.dds.interfaces.gui.dto;

public class TramoHBS {
    private Integer id;
    private TransporteHBS transporte;
    private UbicacionHBS ubicacionInicial;
    private UbicacionHBS ubicacionFinal;
    private Integer idParadaInicial;
    private Integer idParadaFinal;

    public TramoHBS() {
    }

    public Integer getId() {
        return id;
    }

    public UbicacionHBS getUbicacionInicial() {
        return ubicacionInicial;
    }

    public UbicacionHBS getUbicacionFinal() {
        return ubicacionFinal;
    }

    public Integer getIdParadaInicial() {
        return idParadaInicial;
    }

    public Integer getIdParadaFinal() {
        return idParadaFinal;
    }

    public TransporteHBS getTransporte() {
        return transporte;
    }

    public void setTransporte(TransporteHBS transporte) {
        this.transporte = transporte;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUbicacionInicial(UbicacionHBS ubicacionInicial) {
        this.ubicacionInicial = ubicacionInicial;
    }

    public void setUbicacionFinal(UbicacionHBS ubicacionFinal) {
        this.ubicacionFinal = ubicacionFinal;
    }

    public void setIdParadaInicial(Integer idParadaInicial) {
        this.idParadaInicial = idParadaInicial;
    }

    public void setIdParadaFinal(Integer idParadaFinal) {
        this.idParadaFinal = idParadaFinal;
    }
}
