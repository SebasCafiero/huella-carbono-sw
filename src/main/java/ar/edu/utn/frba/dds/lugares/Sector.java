package ar.edu.utn.frba.dds.lugares;

import ar.edu.utn.frba.dds.excepciones.MiembroException;
import ar.edu.utn.frba.dds.excepciones.SectorException;
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

    public Sector(String nombre, Organizacion organizacion) throws SectorException {
        this.nombre = nombre;
        organizacion.agregarSector(this);
        this.organizacion = organizacion;
        this.listaDeMiembros = new HashSet<>();
        this.listaDePostulantes = new HashSet<>();
    }

    public String getNombre() { return this.nombre; }

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

    public void agregarMiembro(Miembro miembro) throws MiembroException{
        if(esMiembro(miembro))
            throw new MiembroException("El miembro ya pertenece a este sector");
        listaDeMiembros.add(miembro);
    }

    public void quitarMiembro(Miembro miembro) throws MiembroException {
        if(!esMiembro(miembro))
            throw new MiembroException("El miembro no pertenece a este sector");
        listaDeMiembros.remove(miembro);
    }

    public void agregarPostulante(Miembro miembro) throws MiembroException{
        if(esPostulante(miembro))
            throw new MiembroException("El miembro ya está postulado a este sector");
        listaDePostulantes.add(miembro);
    }

    public void quitarPostulante(Miembro miembro) throws MiembroException {
        if(!esPostulante(miembro))
            throw new MiembroException("El miembro no está postulado a este sector");
        listaDePostulantes.remove(miembro);
    }

    public Integer cantidadMiembros() {
        return this.listaDeMiembros.size();
    }
}
