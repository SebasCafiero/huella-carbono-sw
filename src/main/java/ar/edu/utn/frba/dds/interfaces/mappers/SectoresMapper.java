package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.interfaces.SectorRequest;
import ar.edu.utn.frba.dds.interfaces.input.SectorConMiembrosDTO;
import ar.edu.utn.frba.dds.interfaces.input.SectorResponse;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;

public class SectoresMapper {

    public static Sector toEntity(SectorRequest sectorResponse, Organizacion unaOrg) {
        return new Sector(sectorResponse.getNombre(), unaOrg);
    }

    public static Sector toEntity(SectorConMiembrosDTO sectorDTO, Organizacion unaOrg) {
        Sector sectorEntity = null;
        try {
            sectorEntity = new Sector(sectorDTO.getNombre(), unaOrg);
            for(MiembroResponse miembroDTO : sectorDTO.getMiembros()) {
                sectorEntity.agregarMiembro(MiembrosMapper.toEntity(miembroDTO));
            }
        } catch (MiembroException | SectorException e) {
            e.printStackTrace();
        }

        return sectorEntity;
    }
}
