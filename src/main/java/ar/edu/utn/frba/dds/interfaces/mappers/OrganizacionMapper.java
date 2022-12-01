package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;

import ar.edu.utn.frba.dds.interfaces.input.SectorResponse;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionConMiembrosJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.SectorConMiembrosDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.OrganizacionResponse;

import java.util.stream.Collectors;

public class OrganizacionMapper {
    public static Organizacion toEntity(OrganizacionJSONDTO orgDTO) {
        Organizacion entity = new Organizacion(
                orgDTO.getNombre(),
                TipoDeOrganizacionEnum.valueOf(orgDTO.getTipo().toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.getClasificacion()),
                UbicacionMapper.toEntity(orgDTO.getUbicacion())
        );

        return entity;
    }

    public static Organizacion toEntity(OrganizacionConMiembrosJSONDTO dto) {
        Organizacion entity = new Organizacion(
                dto.getOrganizacion(),
                TipoDeOrganizacionEnum.valueOf(dto.getTipo().toUpperCase()),
                new ClasificacionOrganizacion(dto.getClasificacion()),
                UbicacionMapper.toEntity(dto.getUbicacion())
        );

        for(SectorConMiembrosDTO sectorDTO : dto.getSectores()) {
//                unaOrg.agregarSector(SectoresMapper.toEntity(sectorDTO, unaOrg));
            SectoresMapper.toEntity(sectorDTO, entity);
        }

        return entity;
    }

    public static OrganizacionResponse toResponse(Organizacion entity) {
        OrganizacionResponse dto = new OrganizacionResponse();
        dto.setOrganizacion(entity.getRazonSocial());
        dto.setTipo(entity.getTipo().name());
        dto.setUbicacion(UbicacionMapper.toDTO(entity.getUbicacion()));
        dto.setSectores(entity.getSectores().stream().map(sector -> {
            SectorResponse sectorResponse = new SectorResponse();
            sectorResponse.setNombre(sector.getNombre());
            sectorResponse.setMiembros(sector.getMiembros().stream().map(MiembrosMapper::toResponse).collect(Collectors.toList()));
            sectorResponse.setId(sector.getId());
            return sectorResponse;
        }).collect(Collectors.toList()));

        return dto;
    }
}
