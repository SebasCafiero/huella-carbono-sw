package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.mapping.SectoresMapper;
import ar.edu.utn.frba.dds.mihuella.dto.ErrorResponse;
import ar.edu.utn.frba.dds.mihuella.dto.MiembroJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.SectorDTO;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserJSON;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
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

    public Object obtener(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        Organizacion organizacion = this.repoOrganizaciones.buscar(Integer.parseInt(request.params("id")));

        if(organizacion == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + request.params("id") + " no existe");
        }

        return "Organizacion " + organizacion.getRazonSocial() + " del tipo"
                + organizacion.getClasificacionOrganizacion();
    }

    public Object eliminar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        Organizacion organizacion = this.repoOrganizaciones.buscar(Integer.parseInt(request.params("id")));

        if(organizacion == null) {
            response.status(400);
            return new ErrorResponse("La organizacion de id " + request.params("id") + " no existe");
        }

        this.repoOrganizaciones.eliminar(organizacion);
        return "Organizacion borrada correctamente";
    }

    public Object modificar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        response.status(400);
        return "Modificacion de organizaciones no está implementada";
    }

    public Object agregar(Request request, Response response) {
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        OrganizacionJSONDTO organizacionJSONDTO = new ParserJSON<>(OrganizacionJSONDTO.class)
                .parseElement(request.body());

        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionJSONDTO);

        for(SectorDTO sectorDTO : organizacionJSONDTO.getSectores()) {
            Sector sector = SectoresMapper.toEntity(sectorDTO, organizacion);

            for(MiembroJSONDTO miembroDTO : sectorDTO.getMiembros()) {
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
        /*if (loginController.chequearValidezAcceso(request, response, true) != null){
            return loginController.chequearValidezAcceso(request, response, true);
        }TODO todo esto agregar una vez que tengamos la vista*/
        List<Organizacion> organizaciones = this.repoOrganizaciones.buscarTodos();
        return organizaciones.toString();
    }
}
