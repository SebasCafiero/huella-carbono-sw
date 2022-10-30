package ar.edu.utn.frba.dds.entities.mediciones;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class ReporteOrganizacion {
    private Float consumoMediciones;
    private Map<Categoria, Float> consumoPorCategoria;
    private Map<Sector, Float> consumoPorSector;
    private Map<Miembro, Float> consumoPorMiembro;
    private Float consumoTotal;
    private LocalDate fechaCreacion;
    private Integer id;
    private Periodo periodoReferencia;

    public ReporteOrganizacion() {
    }

    public ReporteOrganizacion(Map<Categoria, Float> consumoPorCategoria, Map<Sector, Float> consumoPorSector, Map<Miembro, Float> consumoPorMiembro, Float consumoTotal) {
        this.consumoPorCategoria = consumoPorCategoria;
        this.consumoPorSector = consumoPorSector;
        this.consumoPorMiembro = consumoPorMiembro;
        this.consumoTotal = consumoTotal;
    }

    public Float getConsumoMediciones() {
        return consumoMediciones;
    }

    public void setConsumoMediciones(Float consumoMediciones) {
        this.consumoMediciones = consumoMediciones;
    }

    public Map<Categoria, Float> getConsumoPorCategoria() {
        return consumoPorCategoria;
    }

    public void setConsumoPorCategoria(Map<Categoria, Float> consumoPorCategoria) {
        this.consumoPorCategoria = consumoPorCategoria;
    }

    public Map<Sector, Float> getConsumoPorSector() {
        return consumoPorSector;
    }

    public void setConsumoPorSector(Map<Sector, Float> consumoPorSector) {
        this.consumoPorSector = consumoPorSector;
    }

    public Map<Miembro, Float> getConsumoPorMiembro() {
        return consumoPorMiembro;
    }

    public void setConsumoPorMiembro(Map<Miembro, Float> consumoPorMiembro) {
        this.consumoPorMiembro = consumoPorMiembro;
    }

    public Float getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(Float consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Periodo getPeriodoReferencia() {
        return periodoReferencia;
    }

    public void setPeriodoReferencia(Periodo periodoReferencia) {
        this.periodoReferencia = periodoReferencia;
    }
}
