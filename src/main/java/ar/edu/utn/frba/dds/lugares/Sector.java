package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.personas.Miembro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sector {
    private String nombre;
    private Organizacion organizacion;
    private Set<Miembro> listaDeMiembros;
    private Set<Miembro> listaDePostulantes;

    public Sector(String nombre, Organizacion organizacion) throws Exception {
        this.nombre = nombre;
        organizacion.agregarSector(this);
        this.organizacion = organizacion;
        this.listaDeMiembros = new HashSet<>();
        this.listaDePostulantes = new HashSet<>();
    }

    public Set<Miembro> getListaDeMiembros() {
        return this.listaDeMiembros;
    }

    public Organizacion getOrganizacion() {
        return this.organizacion;
    }

    public boolean esMiembro(Miembro miembro) {
        return this.listaDeMiembros.contains(miembro);
    }

    public boolean esPostulante(Miembro miembro) {
        return this.listaDePostulantes.contains(miembro);
    }

    public void agregarMiembro(Miembro miembro) throws Exception{
        if(esMiembro(miembro))
            throw new Exception("El miembro ya pertenece a este sector");
        listaDeMiembros.add(miembro);
    }

    public void quitarMiembro(Miembro miembro) throws Exception {
        if(!esMiembro(miembro))
            throw new Exception("El miembro no pertenece a este sector");
        listaDeMiembros.remove(miembro);
    }

    public void agregarPostulante(Miembro miembro) throws Exception{
        if(esPostulante(miembro))
            throw new Exception("El miembro ya está postulado a este sector");
        listaDePostulantes.add(miembro);
    }

    public void quitarPostulante(Miembro miembro) throws Exception {
        if(!esPostulante(miembro))
            throw new Exception("El miembro no está postulado a este sector");
        listaDePostulantes.remove(miembro);
    }

    public Integer cantidadMiembros() {
        return this.listaDeMiembros.size();
    }
}
