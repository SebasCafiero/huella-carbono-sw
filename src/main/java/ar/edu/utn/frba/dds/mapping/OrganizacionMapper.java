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


public class OrganizacionMapper {

    public static Organizacion toEntity(OrganizacionJSONDTO orgDTO) {
        Organizacion unaOrg = new Organizacion(
                orgDTO.organizacion,
                TipoDeOrganizacionEnum.valueOf(orgDTO.tipo.toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.clasificacion),
                UbicacionMapper.toEntity(orgDTO.ubicacion)
        );
        for(OrganizacionJSONDTO.SectorJSONDTO sectorDTO : orgDTO.sectores) {
            Sector unSector;
            try {
                unSector = new Sector(sectorDTO.nombre, unaOrg);
                for(MiembroJSONDTO unMiembro : sectorDTO.miembros){
                    unSector.agregarMiembro(new Miembro(
                            unMiembro.nombre,
                            unMiembro.apellido,
                            TipoDeDocumento.valueOf(unMiembro.tipoDocumento.toUpperCase()),
                            unMiembro.documento
                    ));
                }
            } catch (SectorException e) {
                e.printStackTrace();
            } catch (MiembroException e) {
                e.printStackTrace();
            }
        }

        return unaOrg;
    }
}
