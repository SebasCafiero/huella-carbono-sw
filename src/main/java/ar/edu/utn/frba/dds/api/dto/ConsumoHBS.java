package ar.edu.utn.frba.dds.api.dto;

import ar.edu.utn.frba.dds.entities.mediciones.ReporteOrganizacion;

import java.util.HashMap;
import java.util.Map;

public class ConsumoHBS {
    private String concepto;
    private Float consumo;

    public ConsumoHBS() {
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Float getConsumo() {
        return consumo;
    }

    public void setConsumo(Float consumo) {
        this.consumo = consumo;
    }
}
