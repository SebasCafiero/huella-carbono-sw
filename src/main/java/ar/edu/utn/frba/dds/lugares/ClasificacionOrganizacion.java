package ar.edu.utn.frba.dds.lugares;

import java.util.List;

public class ClasificacionOrganizacion {
    private String nombre;
    private String[] sectoresMinimos;

    public ClasificacionOrganizacion(String nombre, String[] sectoresMinimos) {
        this.nombre = nombre;
        this.sectoresMinimos = sectoresMinimos;
    }

    public String getNombre() {
        return nombre;
    }

    public String[] getSectoresMinimos() {
        return sectoresMinimos;
    }
}
