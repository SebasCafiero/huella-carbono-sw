package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.util.List;

public class MedicionController {
    private Repositorio<Medicion> repositorio;

    public MedicionController(){
        this.repositorio = FactoryRepositorio.get(Medicion.class);
    }

    public String obtener(Request request, Response response){
        Medicion medicion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        return medicion.getValor().toString();//que deberia devolver?
    }


    public String mostrarTodos(Request request, Response response){
        List<Medicion> mediciones = this.repositorio.buscarTodos();
        return mediciones.toString();
    }

    //faltan hacer los fiiltros --> por ej : mostrar todos las mediciones con "x" unidad
}

