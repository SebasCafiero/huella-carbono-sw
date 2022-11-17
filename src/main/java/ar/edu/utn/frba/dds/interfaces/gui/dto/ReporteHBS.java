package ar.edu.utn.frba.dds.interfaces.gui.dto;

import java.util.List;

public class ReporteHBS {
    private String fechaCreacion;
    private String fechaReferencia;
    private Float consumoTotal;
    private Float consumoMediciones;
    private Float consumoTrayectos;
    private List<ConsumoHBS> consumoPorCategoria;
    private List<ConsumoHBS> consumoPorSector;
    private List<ConsumoHBS> consumoPorMiembro;

    public ReporteHBS() {
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaReferencia() {
        return fechaReferencia;
    }

    public void setFechaReferencia(String fechaReferencia) {
        this.fechaReferencia = fechaReferencia;
    }

    public Float getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(Float consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public Float getConsumoMediciones() {
        return consumoMediciones;
    }

    public void setConsumoMediciones(Float consumoMediciones) {
        this.consumoMediciones = consumoMediciones;
    }

    public Float getConsumoTrayectos() {
        return consumoTrayectos;
    }

    public void setConsumoTrayectos(Float consumoTrayectos) {
        this.consumoTrayectos = consumoTrayectos;
    }

    public List<ConsumoHBS> getConsumoPorCategoria() {
        return consumoPorCategoria;
    }

    public void setConsumoPorCategoria(List<ConsumoHBS> consumoPorCategoria) {
        this.consumoPorCategoria = consumoPorCategoria;
    }

    public List<ConsumoHBS> getConsumoPorSector() {
        return consumoPorSector;
    }

    public void setConsumoPorSector(List<ConsumoHBS> consumoPorSector) {
        this.consumoPorSector = consumoPorSector;
    }

    public List<ConsumoHBS> getConsumoPorMiembro() {
        return consumoPorMiembro;
    }

    public void setConsumoPorMiembro(List<ConsumoHBS> consumoPorMiembro) {
        this.consumoPorMiembro = consumoPorMiembro;
    }
}
