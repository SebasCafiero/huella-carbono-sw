package ar.edu.utn.frba.dds.interfaces.input.csv;

public class TramoCSVDTO {
    private String trayectoId;
    private String tipoDocumento;
    private String miembroDNI;
    private String idCompartido;
    private String latitudInicial;
    private String longitudInicial;
    private String latitudFinal;
    private String longitudFinal;
    private String tipo;
    private String atributo1;
    private String atributo2;
    private String periodicidad;
    private String fecha;

    public TramoCSVDTO() {
    }

    public TramoCSVDTO(String trayectoId, String miembroDNI, String idCompartido, String latitudInicial, String longitudInicial, String latitudFinal, String longitudFinal, String tipo, String atributo1, String atributo2, String periodicidad, String fecha) {
        this.trayectoId = trayectoId;
        this.miembroDNI = miembroDNI;
        this.idCompartido = idCompartido;
        this.latitudInicial = latitudInicial;
        this.longitudInicial = longitudInicial;
        this.latitudFinal = latitudFinal;
        this.longitudFinal = longitudFinal;
        this.tipo = tipo;
        this.atributo1 = atributo1;
        this.atributo2 = atributo2;
        this.periodicidad = periodicidad;
        this.fecha = fecha;
    }

    public String getTrayectoId() {
        return trayectoId;
    }

    public void setTrayectoId(String trayectoId) {
        this.trayectoId = trayectoId;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getMiembroDNI() {
        return miembroDNI;
    }

    public void setMiembroDNI(String miembroDNI) {
        this.miembroDNI = miembroDNI;
    }

    public String getIdCompartido() {
        return idCompartido;
    }

    public void setIdCompartido(String idCompartido) {
        this.idCompartido = idCompartido;
    }

    public String getLatitudInicial() {
        return latitudInicial;
    }

    public void setLatitudInicial(String latitudInicial) {
        this.latitudInicial = latitudInicial;
    }

    public String getLongitudInicial() {
        return longitudInicial;
    }

    public void setLongitudInicial(String longitudInicial) {
        this.longitudInicial = longitudInicial;
    }

    public String getLatitudFinal() {
        return latitudFinal;
    }

    public void setLatitudFinal(String latitudFinal) {
        this.latitudFinal = latitudFinal;
    }

    public String getLongitudFinal() {
        return longitudFinal;
    }

    public void setLongitudFinal(String longitudFinal) {
        this.longitudFinal = longitudFinal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAtributo1() {
        return atributo1;
    }

    public void setAtributo1(String atributo1) {
        this.atributo1 = atributo1;
    }

    public String getAtributo2() {
        return atributo2;
    }

    public void setAtributo2(String atributo2) {
        this.atributo2 = atributo2;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "TramoCSVDTO{" +
                "trayectoId='" + trayectoId + '\'' +
                ", miembroDNI='" + miembroDNI + '\'' +
                ", idCompartido='" + idCompartido + '\'' +
                ", latitudInicial='" + latitudInicial + '\'' +
                ", longitudInicial='" + longitudInicial + '\'' +
                ", latitudFinal='" + latitudFinal + '\'' +
                ", longitudFinal='" + longitudFinal + '\'' +
                ", tipo='" + tipo + '\'' +
                ", atributo1='" + atributo1 + '\'' +
                ", atributo2='" + atributo2 + '\'' +
                ", periodicidad='" + periodicidad + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
