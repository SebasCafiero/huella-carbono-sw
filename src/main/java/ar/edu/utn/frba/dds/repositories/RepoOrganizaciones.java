package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoOrganizaciones {
    List<Organizacion> organizaciones;
    List<Miembro> miembros;
    static RepoOrganizaciones instance;

    public static RepoOrganizaciones getInstance() {
        if (instance==null) {
            instance = new RepoOrganizaciones();
        }
        return instance;
    }

    private RepoOrganizaciones() {
        organizaciones = new ArrayList<Organizacion>();
        miembros = new ArrayList<Miembro>();
    }

    public void agregarOrganizacion(Organizacion organizacion) {
        Optional<Organizacion> optionalOrganizacion = findOrganizacion(organizacion.getRazonSocial());
        if(optionalOrganizacion.isPresent()) {
//            throw new Exception("Ya existe la organizacion");
        }
        organizaciones.add(organizacion);
    }

    public void agregarMiembro(Miembro miembro) {
        Optional<Miembro> optionalMiembro = findMiembro(miembro.getNroDocumento());
        if(optionalMiembro.isPresent()) {
//            throw new Exception("Ya existe la organizacion");
        }
        miembros.add(miembro);
    }

    public Optional<Miembro> findMiembro(int nroDocumento) {
        return miembros.stream().filter( me -> me.getNroDocumento().equals(nroDocumento)).findFirst();
    }


    public List<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public Optional<Organizacion> findOrganizacion(String nombreOrganizacion) {
        return organizaciones.stream().filter( me -> me.getRazonSocial().equals(nombreOrganizacion)).findFirst();
    }
}
