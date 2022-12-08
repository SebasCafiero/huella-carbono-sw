package ar.edu.utn.frba.dds.entities.medibles;

import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

import java.time.LocalDate;
import java.util.*;

public class ReporteArea {
    private Map<Organizacion, Float> hcOrganizaciones;
    private AreaSectorial area;
    private Float hcTotal;
    private LocalDate fechaCreacion;

    public ReporteArea(Map<Organizacion, Float> hcOrganizaciones,
                         AreaSectorial area, Float hcTotal) {
        this.hcOrganizaciones = hcOrganizaciones;
        this.area = area;
        this.hcTotal = hcTotal;
    }

    public Set<Organizacion> getOrganizaciones() {
        return hcOrganizaciones.keySet();
    }

    public Map<Organizacion, Float> getHcOrganizaciones() {
        return hcOrganizaciones;
    }

    public Float getHcTotal() {
        return hcTotal;
    }

    public AreaSectorial getArea() {
        return area;
    }

    public void setHcOrganizaciones(Map<Organizacion, Float> hcOrganizaciones) {
        this.hcOrganizaciones = hcOrganizaciones;
    }

    public void setArea(AreaSectorial area) {
        this.area = area;
    }

    public void setHcTotal(Float hcTotal) {
        this.hcTotal = hcTotal;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
