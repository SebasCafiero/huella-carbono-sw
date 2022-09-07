package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

public class MiembroController {
    private Repositorio<Miembro> repositorio;

    public MiembroController(){
        this.repositorio = FactoryRepositorio.get(Miembro.class);
    }
}
