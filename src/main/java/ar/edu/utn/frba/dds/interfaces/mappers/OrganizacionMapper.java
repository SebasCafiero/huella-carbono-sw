package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;

import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionConMiembrosJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.SectorConMiembrosDTO;


public class OrganizacionMapper {
    public static Organizacion toEntity(OrganizacionJSONDTO orgDTO) {
        Organizacion unaOrg = new Organizacion(
                orgDTO.getOrganizacion(),
                TipoDeOrganizacionEnum.valueOf(orgDTO.getTipo().toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.getClasificacion()),
                UbicacionMapper.toEntity(orgDTO.getUbicacion())
        );

        return unaOrg;
    }

    public static Organizacion toEntity(OrganizacionConMiembrosJSONDTO orgDTO) {
        Organizacion unaOrg = new Organizacion(
                orgDTO.getOrganizacion(),
                TipoDeOrganizacionEnum.valueOf(orgDTO.getTipo().toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.getClasificacion()),
                UbicacionMapper.toEntity(orgDTO.getUbicacion())
        );

        for(SectorConMiembrosDTO sectorDTO : orgDTO.getSectores()) {
//                unaOrg.agregarSector(SectoresMapper.toEntity(sectorDTO, unaOrg));
            SectoresMapper.toEntity(sectorDTO, unaOrg);
        }

        return unaOrg;
    }
}
