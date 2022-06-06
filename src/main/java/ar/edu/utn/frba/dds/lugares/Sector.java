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
    private Set<Miembro> miembros;

    public Sector(String nombre, Organizacion organizacion) throws SectorException {
        this.nombre = nombre;
        organizacion.agregarSector(this);
        this.organizacion = organizacion;
        this.miembros = new HashSet<>();
    }

    public String getNombre() { return this.nombre; }

    public Set<Miembro> getListaDeMiembros() {
        return this.miembros;
    }

    public Organizacion getOrganizacion() {
        return this.organizacion;
    }

    public boolean esMiembro(Miembro miembro) {
        return this.miembros.contains(miembro);
    }

    public void agregarMiembro(Miembro miembro) throws MiembroException{
        if(esMiembro(miembro))
            throw new MiembroException("El miembro ya pertenece a este sector");
        miembros.add(miembro);
        miembro.agregarSector(this);
    }

    public void quitarMiembro(Miembro miembro) throws MiembroException {
        if(!esMiembro(miembro))
            throw new MiembroException("El miembro no pertenece a este sector");
        miembros.remove(miembro);
        miembro.quitarSector(this);
    }

    public Integer cantidadMiembros() {
        return this.miembros.size();
    }
}
