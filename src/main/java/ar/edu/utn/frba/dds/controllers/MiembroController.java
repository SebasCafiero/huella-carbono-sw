package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.server.login.Filtrador;
import ar.edu.utn.frba.dds.server.login.ForbiddenException;
import ar.edu.utn.frba.dds.server.login.NotFoundException;
import ar.edu.utn.frba.dds.server.login.UnauthorizedException;
import ar.edu.utn.frba.dds.interfaces.mappers.MiembrosMapper;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

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

        model.put("descripcion", miembro.nombreCompleto());

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
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        return "Modificacion de miembros no implementada";
    }

    public Object agregar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/

        MiembroResponse miembroCSVDTO = new ParserJSON<>(MiembroResponse.class).parseElement(request.body());

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
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/

        List<Miembro> organizaciones = this.repoMiembros.buscarTodos();
        return organizaciones.toString();
    }
}
