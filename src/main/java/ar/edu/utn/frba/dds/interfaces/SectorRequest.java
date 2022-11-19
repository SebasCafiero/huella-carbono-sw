package ar.edu.utn.frba.dds.interfaces;

import ar.edu.utn.frba.dds.interfaces.input.json.MiembroIdentityRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;

import java.util.List;

public class SectorRequest {
    private String nombre;
    private List<MiembroIdentityRequest> miembros;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MiembroIdentityRequest> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroIdentityRequest> miembros) {
        this.miembros = miembros;
    }
}
