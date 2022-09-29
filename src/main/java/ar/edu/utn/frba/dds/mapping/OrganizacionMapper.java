package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;

import ar.edu.utn.frba.dds.mihuella.dto.MiembroJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;

import java.util.HashSet;
import java.util.Set;


public class OrganizacionMapper {

    public static Organizacion toEntity(OrganizacionJSONDTO orgDTO) {
        Organizacion unaOrg = new Organizacion(
                orgDTO.organizacion,
                TipoDeOrganizacionEnum.valueOf(orgDTO.tipo.toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.clasificacion),
                UbicacionMapper.toEntity(orgDTO.ubicacion)
        );

        for(OrganizacionJSONDTO.SectorJSONDTO sectorDTO : orgDTO.sectores) {
//                unaOrg.agregarSector(SectoresMapper.toEntity(sectorDTO, unaOrg));
            SectoresMapper.toEntity(sectorDTO, unaOrg);
        }




        return unaOrg;
    }
}
