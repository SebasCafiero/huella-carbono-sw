package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.interfaces.SectorRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroIdentityRequest;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.repositories.RepoMiembros;
import ar.edu.utn.frba.dds.repositories.RepoOrganizaciones;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;

import javax.persistence.EntityNotFoundException;
import java.util.*;

public class FachadaOrganizacionApi {
    private final Repositorio<ClasificacionOrganizacion> repoClasificaciones;
    private final RepoOrganizaciones repoOrganizaciones;
    private final Repositorio<Municipio> repoAreas;
    private final RepoMiembros repoMiembros;

    public FachadaOrganizacionApi() {
        this.repoClasificaciones = FactoryRepositorio.get(ClasificacionOrganizacion.class);
        this.repoAreas = FactoryRepositorio.get(Municipio.class);
        this.repoMiembros = (RepoMiembros) FactoryRepositorio.get(Miembro.class);
        this.repoOrganizaciones = (RepoOrganizaciones) FactoryRepositorio.get(Organizacion.class);
    }

    public Organizacion agregar(OrganizacionJSONDTO orgRequest) {
        ClasificacionOrganizacion clasificacion = this.repoClasificaciones.buscarTodos().stream()
                .filter(cl -> cl.getNombre().equals(orgRequest.getClasificacion()))
                .findFirst().orElse(new ClasificacionOrganizacion(orgRequest.getClasificacion()));

        Municipio area = this.repoAreas.buscarTodos().stream()
                .filter(cl -> cl.getNombre().equals(orgRequest.getUbicacion().getDireccion().getMunicipio()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("El municipio indicado no existe"));

        UbicacionGeografica ubicacion = new UbicacionGeografica(area,
                orgRequest.getUbicacion().getDireccion().getLocalidad(),
                orgRequest.getUbicacion().getDireccion().getCalle(),
                orgRequest.getUbicacion().getDireccion().getNumero(),
                new Coordenada(orgRequest.getUbicacion().getCoordenadas().getLatitud(),
                        orgRequest.getUbicacion().getCoordenadas().getLatitud()));

        Organizacion organizacion = new Organizacion();
        organizacion.setRazonSocial(orgRequest.getOrganizacion());
        organizacion.setTipo(TipoDeOrganizacionEnum.valueOf(orgRequest.getTipo().toUpperCase()));
        organizacion.setClasificacionOrganizacion(clasificacion);
        organizacion.setUbicacion(ubicacion);

        for(SectorRequest sectorRequest : orgRequest.getSectores()) {
            Sector sector = new Sector(sectorRequest.getNombre(), organizacion);

            for(MiembroIdentityRequest miembroDTO : sectorRequest.getMiembros()) {
                Optional<Miembro> miembro = this.repoMiembros.findByDocumento(
                        TipoDeDocumento.valueOf(miembroDTO.getTipoDocumento()), miembroDTO.getDocumento());

                if(miembro.isPresent()) {
                    sector.agregarMiembro(miembro.get());
                } else {
                    throw new EntityNotFoundException("No existe el miembro de con documento "
                            + miembroDTO.getTipoDocumento() + " y numero " + miembroDTO.getDocumento());
                }
            }
        }

        return this.repoOrganizaciones.agregar(organizacion);
    }
}
