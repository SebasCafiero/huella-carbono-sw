package ar.edu.utn.frba.dds.interfaces.controllers;

import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.interfaces.RequestInvalidoApiException;
import ar.edu.utn.frba.dds.interfaces.SectorRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionResponse;
import ar.edu.utn.frba.dds.interfaces.mappers.OrganizacionMapper;
import ar.edu.utn.frba.dds.interfaces.mappers.SectoresMapper;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroIdentityRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.interfaces.mappers.UbicacionMapper;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.servicios.fachadas.FachadaOrganizacionApi;
import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.MiHuellaApiException;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public class OrganizacionController {
    private final Repositorio<Organizacion> repoOrganizaciones;
    private final RepoMiembros repoMiembros;
    private final FachadaOrganizacionApi fachadaOrganizacion;

    public OrganizacionController() {
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
        this.fachadaOrganizacion = new FachadaOrganizacionApi();
    }

    public OrganizacionResponse obtener(Request request, Response response) {
        Organizacion organizacion = this.repoOrganizaciones.buscar(Integer.parseInt(request.params("id"))).get();
        return OrganizacionMapper.toResponse(organizacion);
    }

    public Object eliminar(Request request, Response response) {
        Organizacion organizacion = this.repoOrganizaciones.buscar(Integer.parseInt(request.params("id"))).get();

        this.repoOrganizaciones.eliminar(organizacion);
        return "Organizacion borrada correctamente";
    }

    public Object modificar(Request request, Response response) {
        response.status(400);
        return "Modificacion de organizaciones no está implementada";
    }

    public Object agregar(Request request, Response response) {
        OrganizacionJSONDTO orgRequest = new ParserJSON<>(OrganizacionJSONDTO.class)
                .parseElement(request.body());

        Organizacion organizacion;
        try {
            organizacion = this.fachadaOrganizacion.agregar(orgRequest);
        } catch (EntityNotFoundException e) {
            response.status(HttpStatus.CONFLICT_409);
            throw new MiHuellaApiException(e.getMessage());
        }
        return organizacion;
    }

    public String mostrarTodos(Request request, Response response){
        List<Organizacion> organizaciones = this.repoOrganizaciones.buscarTodos();
        return organizaciones.toString();
    }


    public Void agregarMiembro(Request request, Response response) {
        Organizacion organizacion = this.repoOrganizaciones.buscar(Integer.parseInt(request.params("id"))).get();

        Sector sector = organizacion.getSectores().stream()
                .filter(sec -> sec.getId().equals(Integer.parseInt(request.params("sector"))))
                .findFirst().orElseThrow(() -> new RequestInvalidoApiException("No existe el sector"));

        MiembroIdentityRequest miembroRequest = new ParserJSON<>(MiembroIdentityRequest.class)
                .parseElement(request.body());

        Miembro miembro = repoMiembros.findByDocumento(
                TipoDeDocumento.valueOf(miembroRequest.getTipoDocumento()), miembroRequest.getDocumento())
//                .orElseThrow(MiembroException::new);
                .orElseThrow(() -> new MiHuellaApiException("No existe el miembro de tipo de documento " +
                        miembroRequest.getTipoDocumento() + " y nro de documento "+ miembroRequest.getDocumento()));

        if (organizacion.getMiembros().stream().anyMatch(mi -> mi.getId().equals(miembro.getId()))) {
            throw new MiHuellaApiException("El miembro ya trabaja en la organización");
        }

        sector.agregarMiembro(miembro);
        response.status(HttpStatus.CREATED_201);
        return null;
    }
}
