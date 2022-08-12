package ar.edu.utn.frba.dds.entities.mediciones;

import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

import java.time.LocalDate;
import java.util.*;

public class Reporte {
    private Set<Organizacion> organizaciones;
    private Map<Organizacion,Float> hcOrganizaciones;
    private AreaSectorial area;
    private Float hcTotal;
    private LocalDate fecha;

    public Reporte(Set<Organizacion> organizaciones, Map<Organizacion, Float> hcOrganizaciones,
                   AreaSectorial area, Float hcTotal) {
        this.organizaciones = organizaciones;
        this.hcOrganizaciones = hcOrganizaciones;
        this.area = area;
        this.hcTotal = hcTotal;
        this.fecha = LocalDate.now();
    }

    public Set<Organizacion> getOrganizaciones() {
        return organizaciones;
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

    public LocalDate getFecha() {
        return fecha;
    }
}
