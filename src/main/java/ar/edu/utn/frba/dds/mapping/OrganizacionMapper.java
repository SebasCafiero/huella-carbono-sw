package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;

import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionConMiembrosJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.SectorConMiembrosDTO;
import ar.edu.utn.frba.dds.mihuella.dto.SectorDTO;


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
