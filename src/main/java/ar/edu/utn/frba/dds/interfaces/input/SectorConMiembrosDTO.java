package ar.edu.utn.frba.dds.interfaces.input;

import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;

import java.util.List;

public class SectorConMiembrosDTO {
    private String nombre;
    private List<MiembroResponse> miembros;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MiembroResponse> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroResponse> miembros) {
        this.miembros = miembros;
    }
}
