package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.interfaces.SectorRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionResponse;
import ar.edu.utn.frba.dds.interfaces.mappers.OrganizacionMapper;
import ar.edu.utn.frba.dds.interfaces.mappers.SectoresMapper;
import ar.edu.utn.frba.dds.interfaces.gui.dto.ErrorResponse;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroIdentityRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.SectorResponse;
import ar.edu.utn.frba.dds.interfaces.input.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

public class OrganizacionController {
    private final Repositorio<Organizacion> repoOrganizaciones;
    private final RepoMiembros repoMiembros;

    public OrganizacionController() {
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
    }

    public OrganizacionResponse obtener(Request request, Response response) {
        return OrganizacionMapper.toResponse(request.attribute("organizacion"));
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

        OrganizacionJSONDTO organizacionJSONDTO = new ParserJSON<>(OrganizacionJSONDTO.class)
                .parseElement(request.body());

        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionJSONDTO);

        for(SectorRequest sectorRequest : organizacionJSONDTO.getSectores()) {
            Sector sector = SectoresMapper.toEntity(sectorRequest, organizacion);

            for(MiembroIdentityRequest miembroDTO : sectorRequest.getMiembros()) {
                Optional<Miembro> miembro = this.repoMiembros.findByDocumento(
                        TipoDeDocumento.valueOf(miembroDTO.getTipoDocumento()), miembroDTO.getDocumento());

                if(miembro.isPresent()) {
                    sector.agregarMiembro(miembro.get());
                } else {
                    response.status(400);
                    return new ErrorResponse("No existe el miembro de con documento "
                            + miembroDTO.getTipoDocumento() + " y numero " + miembroDTO.getDocumento());
                }
            }

            organizacion.agregarSector(sector);
        }

        this.repoOrganizaciones.agregar(organizacion);
        return "Agregaste correctamente la organizacion";
    }

    public String mostrarTodos(Request request, Response response){

        List<Organizacion> organizaciones = this.repoOrganizaciones.buscarTodos();
        return organizaciones.toString();
    }
}
