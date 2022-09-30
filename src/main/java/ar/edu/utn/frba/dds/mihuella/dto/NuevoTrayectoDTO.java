package ar.edu.utn.frba.dds.mihuella.dto;

public class NuevoTrayectoDTO {
    private Integer trayectoId;
    private Integer miembroDNI;
    private Boolean compartidoPasivo;
    private Float latitudInicial;
    private Float longitudInicial;
    private Float latitudFinal;
    private Float longitudFinal;
    private String tipoMedio;
    private String atributo1;
    private String atributo2;
    private Character periodicidad;
    private Integer anio;
    private Integer mes;

    public NuevoTrayectoDTO() {
    }

    public NuevoTrayectoDTO(Integer trayectoId, Integer miembroDNI, Boolean compartidoPasivo, Float latitudInicial, Float longitudInicial, Float latitudFinal, Float longitudFinal, String tipoMedio, String atributo1, String atributo2, Character periodicidad, Integer anio, Integer mes) {
        this.trayectoId = trayectoId;
        this.miembroDNI = miembroDNI;
        this.compartidoPasivo = compartidoPasivo;
        this.latitudInicial = latitudInicial;
        this.longitudInicial = longitudInicial;
        this.latitudFinal = latitudFinal;
        this.longitudFinal = longitudFinal;
        this.tipoMedio = tipoMedio;
        this.atributo1 = atributo1;
        this.atributo2 = atributo2;
        this.periodicidad = periodicidad;
        this.anio = anio;
        this.mes = mes;
    }

    public Integer getTrayectoId() {
        return trayectoId;
    }

    public void setTrayectoId(Integer trayectoId) {
        this.trayectoId = trayectoId;
    }

    public Integer getMiembroDNI() {
        return miembroDNI;
    }

    public void setMiembroDNI(Integer miembroDNI) {
        this.miembroDNI = miembroDNI;
    }

    public Boolean getCompartidoPasivo() {
        return compartidoPasivo;
    }

    public void setCompartidoPasivo(Boolean compartidoPasivo) {
        this.compartidoPasivo = compartidoPasivo;
    }

    public Float getLatitudInicial() {
        return latitudInicial;
    }

    public void setLatitudInicial(Float latitudInicial) {
        this.latitudInicial = latitudInicial;
    }

    public Float getLongitudInicial() {
        return longitudInicial;
    }

    public void setLongitudInicial(Float longitudInicial) {
        this.longitudInicial = longitudInicial;
    }

    public Float getLatitudFinal() {
        return latitudFinal;
    }

    public void setLatitudFinal(Float latitudFinal) {
        this.latitudFinal = latitudFinal;
    }

    public Float getLongitudFinal() {
        return longitudFinal;
    }

    public void setLongitudFinal(Float longitudFinal) {
        this.longitudFinal = longitudFinal;
    }

    public String getTipoMedio() {
        return tipoMedio;
    }

    public void setTipoMedio(String tipoMedio) {
        this.tipoMedio = tipoMedio;
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

    public Character getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Character periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }
}
