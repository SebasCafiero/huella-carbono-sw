package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.login.Filtrador;
import ar.edu.utn.frba.dds.login.ForbiddenException;
import ar.edu.utn.frba.dds.login.UnauthorizedException;
import ar.edu.utn.frba.dds.mapping.MiembrosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.*;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
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

    public Object obtener(Request request, Response response) {
        try{
            Filtrador.filtrarLogueo(request, response);
            Filtrador.filtrarPorRol(request, response, "miembro");
        }catch (ForbiddenException | UnauthorizedException e){
            //response.redirect("error.hbs");
            HashMap<String, Object> model = new HashMap<>();
            model.put("codigo", e.getCodigo());
            model.put("descripcion", e.getMessage());

            return new ModelAndView(model, "error.hbs");
        }


        Miembro miembro = this.repoMiembros.buscar(Integer.parseInt(request.params("id")));

        if(miembro == null) {
            response.status(400);
            return new ErrorResponse("El miembro de id " + request.params("id") + " no existe");
        }

        return miembro.nombreCompleto();
    }

    public Object eliminar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/

        Miembro miembro = this.repoMiembros.buscar(Integer.parseInt(request.params("id")));

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
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/

        List<Miembro> organizaciones = this.repoMiembros.buscarTodos();
        return organizaciones.toString();
    }
}
