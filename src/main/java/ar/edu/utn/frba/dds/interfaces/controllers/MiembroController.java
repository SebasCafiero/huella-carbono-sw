package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.interfaces.input.ErrorDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.NuevoMiembroRequest;
import ar.edu.utn.frba.dds.interfaces.mappers.MiembrosMapper;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.MiHuellaApiException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import javax.persistence.PersistenceException;
import java.util.List;

public class MiembroController {
    private final RepoMiembros repoMiembros;

    public MiembroController() {
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
    }

    public Object eliminar(Request request, Response response) {
        Miembro miembro = this.repoMiembros.buscar(Integer.parseInt(request.params("id"))).get();

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

    public Miembro agregar(Request request, Response response) {
        NuevoMiembroRequest miembroRequest = new ParserJSON<>(NuevoMiembroRequest.class).parseElement(request.body());
        Miembro miembro = MiembrosMapper.toEntity(miembroRequest);

        try {
            miembro = this.repoMiembros.agregar(miembro);
        } catch (PersistenceException e) {
            throw new MiHuellaApiException(new ErrorDTO("Ya existe un miembro con el mismo par tipo " +
                    "documento + numero documento"));
        }

        response.status(HttpStatus.CREATED_201);
        return miembro;
    }

    public String mostrarTodos(Request request, Response response){
        List<Miembro> organizaciones = this.repoMiembros.buscarTodos();
        return organizaciones.toString();
    }
}
