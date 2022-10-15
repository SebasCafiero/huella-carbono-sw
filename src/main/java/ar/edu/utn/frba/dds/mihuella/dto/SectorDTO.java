package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;

public class SectorDTO {
    private String nombre;
    private List<MiembroJSONDTO> miembros;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MiembroJSONDTO> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroJSONDTO> miembros) {
        this.miembros = miembros;
    }
}
