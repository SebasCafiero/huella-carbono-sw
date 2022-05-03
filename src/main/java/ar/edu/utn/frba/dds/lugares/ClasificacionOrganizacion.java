package ar.edu.utn.frba.dds.lugares;

import java.util.List;

public class ClasificacionOrganizacion {
    private String nombre;
    private List<String> sectoresMinimos;

    public ClasificacionOrganizacion(String nombre, List<String> sectoresMinimos) {
        this.nombre = nombre;
        this.sectoresMinimos = sectoresMinimos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getSectoresMinimos() {
        return sectoresMinimos;
    }
}
