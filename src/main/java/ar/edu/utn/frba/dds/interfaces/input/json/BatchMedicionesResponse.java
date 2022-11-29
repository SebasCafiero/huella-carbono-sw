package ar.edu.utn.frba.dds.interfaces.input.json;

import java.time.LocalDate;
import java.util.List;

public class BatchMedicionesResponse {
    private Integer id;
    private Integer cantidad;
    private LocalDate fecha;
    private List<MedicionJSONDTO> mediciones;

    public BatchMedicionesResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<MedicionJSONDTO> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<MedicionJSONDTO> mediciones) {
        this.mediciones = mediciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
