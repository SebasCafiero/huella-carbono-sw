package lugares;

import personas.Miembro;

import java.util.ArrayList;
import java.util.List;

public class Sector {
    private String nombre;
    private Organizacion organizacionAlQueCorresponde;
    private List<Miembro> listaDeMiembros;

    public Sector() {
        listaDeMiembros = new ArrayList<>();
    }

    public List<Miembro> getListaDeMiembros() {
        return listaDeMiembros;
    }

    public Organizacion getOrganizacionAlQueCorresponde() {
        return organizacionAlQueCorresponde;
    }
}
