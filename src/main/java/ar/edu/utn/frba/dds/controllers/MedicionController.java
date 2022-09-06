package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MedicionController {
    private Repositorio<Medicion> repositorio;

    public MedicionController(){
        this.repositorio = FactoryRepositorio.get(Medicion.class);
    }

    public String obtener(Request request, Response response){
        Medicion medicion = this.repositorio.buscar(Integer.valueOf(request.params("id")));
        return medicion.toString();
    }

    public String mostrarTodos(Request request, Response response){
        List<Medicion> mediciones = this.repositorio.buscarTodos();
        return mediciones.toString();
    }

    public String filtrarUnidad(Request request, Response response) {
        List<Medicion> mediciones = this.repositorio.buscarTodos();
        return  mediciones
                .stream()
                .filter(m -> Objects.equals(m.getUnidad(), String.valueOf(request.params("unidad"))))
                .collect(Collectors.toList())
                .toString();
    }

    public String filtrarValor(Request request, Response response) {
        List<Medicion> mediciones = this.repositorio.buscarTodos();
        return  mediciones
                .stream()
                .filter(m -> Objects.equals(m.getValor(), Float.valueOf(request.params("valor"))))
                .collect(Collectors.toList())
                .toString();
    }
}

