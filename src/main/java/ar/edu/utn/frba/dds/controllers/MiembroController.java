package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.mapping.MiembrosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.*;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.util.List;

public class MiembroController {
    private final RepoMiembros repoMiembros;

    public MiembroController() {
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
    }

    public Object obtener(Request request, Response response) {
        Miembro miembro = this.repoMiembros.buscar(Integer.parseInt(request.params("id")));

        if(miembro == null) {
            response.status(400);
            return new ErrorResponse("El miembro de id " + request.params("id") + " no existe");
        }

        return miembro.nombreCompleto();
    }

    public Object eliminar(Request request, Response response) {
        Miembro miembro = this.repoMiembros.buscar(Integer.parseInt(request.params("id")));

        if(miembro == null) {
            response.status(400);
            return new ErrorResponse("El miembro de id " + request.params("id") + " no existe");
        }

        this.repoMiembros.eliminar(miembro);
        return "Organizacion borrada correctamente";
    }

    public Object modificar(Request request, Response response) {
        return "Modificacion de miembros no implementada";
    }

    public Object agregar(Request request, Response response) {
        MiembroCSVDTO miembroCSVDTO = new ParserJSON<>(MiembroCSVDTO.class).parseElement(request.body());

        Miembro miembro = MiembrosMapper.toEntity(miembroCSVDTO);

        if(repoMiembros.findByDocumento(miembro.getTipoDeDocumento(), miembro.getNroDocumento()).isPresent()) {
            response.status(400);
            return new ErrorResponse("Ya existe un miembro con el documento "
                    + miembro.getTipoDeDocumento() + " " + miembro.getNroDocumento());
        }

        this.repoMiembros.agregar(miembro);
        return "Agregaste correctamente el miembro";
    }

    public String mostrarTodos(Request request, Response response){
        List<Miembro> organizaciones = this.repoMiembros.buscarTodos();
        return organizaciones.toString();
    }
}
