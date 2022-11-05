package ar.edu.utn.frba.dds.mihuella.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.processor.PreAssignmentProcessor;

public class TramoCSVDTO2 {

    @CsvBindByName(column = "TrayectoId")
    private int idTrayecto;

    @CsvBindByName(column = "MiembroDNI")
    private int idMiembro;

    @CsvBindByName(column = "IdCompartido")
    private int idTrayectoCompartido;

    @CsvBindByName(column = "LatitudInicial")
    private float latInicial;

    @CsvBindByName(column = "LongitudInicial")
    private float longInicial;

    @CsvBindByName(column = "LatitudFinal")
    private float latFinal;

    @CsvBindByName(column = "LongitudFinal")
    private float longFinal;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Tipo")
    private String tipoTransporte;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Atributo1")
    private String subtipoTransporte;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Atributo2")
    private String info;

    @CsvBindByName(column = "Periodicidad")
    private char periodicidad;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Fecha")
    private String fecha;

    public TramoCSVDTO2() {

    }

    public int getIdTrayecto() {
        return idTrayecto;
    }

    public void setIdTrayecto(int idTrayecto) {
        this.idTrayecto = idTrayecto;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getIdTrayectoCompartido() {
        return idTrayectoCompartido;
    }

    public void setIdTrayectoCompartido(int idTrayectoCompartido) {
        this.idTrayectoCompartido = idTrayectoCompartido;
    }

    public float getLatInicial() {
        return latInicial;
    }

    public void setLatInicial(float latInicial) {
        this.latInicial = latInicial;
    }

    public float getLongInicial() {
        return longInicial;
    }

    public void setLongInicial(float longInicial) {
        this.longInicial = longInicial;
    }

    public float getLatFinal() {
        return latFinal;
    }

    public void setLatFinal(float latFinal) {
        this.latFinal = latFinal;
    }

    public float getLongFinal() {
        return longFinal;
    }

    public void setLongFinal(float longFinal) {
        this.longFinal = longFinal;
    }

    public String getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public String getSubtipoTransporte() {
        return subtipoTransporte;
    }

    public void setSubtipoTransporte(String subtipoTransporte) {
        this.subtipoTransporte = subtipoTransporte;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public char getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(char periodicidad) {
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
        return "TramoCSVDTO2{" +
                "idTrayecto=" + idTrayecto +
                ", idMiembro=" + idMiembro +
                ", idTrayectoCompartido=" + idTrayectoCompartido +
                ", latInicial=" + latInicial +
                ", longInicial=" + longInicial +
                ", latFinal=" + latFinal +
                ", longFinal=" + longFinal +
                ", tipoTransporte='" + tipoTransporte + '\'' +
                ", subtipoTransporte='" + subtipoTransporte + '\'' +
                ", info='" + info + '\'' +
                ", periodicidad=" + periodicidad +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    //CsvToBeanBuilder.withOrderedResults(false)
}
