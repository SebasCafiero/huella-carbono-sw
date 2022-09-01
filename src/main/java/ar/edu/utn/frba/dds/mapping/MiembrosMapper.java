package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import java.util.Set;

public class MiembrosMapper {
    private static Repositorio<Miembro> repositorio;

    public static void map(int id, Set<Miembro> miembros){
        repositorio = FactoryRepositorio.get(Miembro.class);
        Miembro miembro = repositorio.buscar(id);
        miembros.add(miembro);
    }
}