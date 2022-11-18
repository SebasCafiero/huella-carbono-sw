package ar.edu.utn.frba.dds.interfaces.input;

import ar.edu.utn.frba.dds.interfaces.input.json.MiembroJSONDTO;

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
