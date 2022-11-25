package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.interfaces.input.ErrorDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.NuevoMiembroRequest;
import ar.edu.utn.frba.dds.server.login.Filtrador;
import ar.edu.utn.frba.dds.server.login.ForbiddenException;
import ar.edu.utn.frba.dds.server.login.NotFoundException;
import ar.edu.utn.frba.dds.server.login.UnauthorizedException;
import ar.edu.utn.frba.dds.interfaces.mappers.MiembrosMapper;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.MiHuellaApiException;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.List;

public class MiembroController {
    private final RepoMiembros repoMiembros;

    public MiembroController() {
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
    }

    public ModelAndView obtener(Request request, Response response) {
        HashMap<String, Object> model = new HashMap<>();
        try{
            Filtrador.filtrarLogueo(request, response);
            Filtrador.filtrarPorRol(request, response, "miembro");
            Filtrador.filtrarExistenciaRecurso(request, response, Miembro.class, Integer.parseInt(request.params("id")));
            Filtrador.filtrarPorId(request, response, Integer.parseInt(request.params("id")), "miembro");
        }catch (ForbiddenException | UnauthorizedException | NotFoundException e){
            model.put("descripcion", e.getMessage());
            model.put("codigo", response.status());

            return new ErrorResponse().generarVista(model);
        }
        Miembro miembro = this.repoMiembros.buscar(Integer.parseInt(request.params("id"))).get();

        model.put("descripcion", miembro.getNombreCompleto());

        return new ModelAndView(model, "mensaje.hbs");
    }

    public Object eliminar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/

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
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/

        List<Miembro> organizaciones = this.repoMiembros.buscarTodos();
        return organizaciones.toString();
    }
}
