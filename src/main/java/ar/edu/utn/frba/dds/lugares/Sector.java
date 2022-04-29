package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.personas.Miembro;

import java.util.ArrayList;
import java.util.List;

public class Sector {
    private String nombre;
    private Organizacion organizacion;
    private List<Miembro> listaDeMiembros;
    private List<Miembro> postulantes;

    public Sector(String nombre, Organizacion organizacion) throws Exception {
        this.nombre = nombre;
        organizacion.agregarSector(this);
        this.organizacion = organizacion;
        this.listaDeMiembros = new ArrayList<>();
    }

    public List<Miembro> getListaDeMiembros() {
        return listaDeMiembros;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public boolean esMiembro(Miembro miembro) {
        return listaDeMiembros.contains(miembro);
    }

    public void agregarMiembro(Miembro miembro) throws Exception{
        if(!this.listaDeMiembros.contains(miembro))
            throw new Exception();
        listaDeMiembros.add(miembro);
    }

    public void quitarMiembro(Miembro miembro) {
        listaDeMiembros.remove(miembro);
    }
}
