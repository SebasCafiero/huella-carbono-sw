package ar.edu.utn.frba.dds.interfaces.input;

import ar.edu.utn.frba.dds.interfaces.input.csv.MiembroCSVDTO;

import java.util.List;

public class SectorConMiembrosDTO {
    private String nombre;
    private List<MiembroCSVDTO> miembros;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MiembroCSVDTO> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroCSVDTO> miembros) {
        this.miembros = miembros;
    }
}