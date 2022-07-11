package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Reporte {
    private Set<Organizacion> organizaciones;
    private Map<Organizacion,Float> hcOrganizaciones;
    private UbicacionGeografica ubicacion;
    private Float hcTotal;
    private LocalDate fecha;

    public Reporte(Set<Organizacion> organizaciones, Map<Organizacion, Float> hcOrganizaciones,
                   UbicacionGeografica ubicacion, Float hcTotal) {
        this.organizaciones = organizaciones;
        this.hcOrganizaciones = hcOrganizaciones;
        this.ubicacion = ubicacion;
        this.hcTotal = hcTotal;
        this.fecha = LocalDate.now();
    }

    public void notificar() {
        List<String> destinatarios = this.organizaciones.stream()
                .flatMap(org -> org.getContactosMail().stream()).collect(Collectors.toList());
        //System.out.println();
        // TODO enviar mails a contactos
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

    public UbicacionGeografica getUbicacion() {
        return ubicacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
