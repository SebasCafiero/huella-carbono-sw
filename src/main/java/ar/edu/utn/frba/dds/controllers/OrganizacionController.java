package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;

import java.util.List;

public class OrganizacionController {
    private Repositorio<Organizacion> repositorio;

    public OrganizacionController(){
        this.repositorio = FactoryRepositorio.get(Organizacion.class);
    }
}
