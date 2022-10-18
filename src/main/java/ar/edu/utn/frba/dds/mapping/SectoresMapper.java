package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.mihuella.dto.*;

public class SectoresMapper {

    public static Sector toEntity(SectorDTO sectorDTO, Organizacion unaOrg) {
        return new Sector(sectorDTO.getNombre(), unaOrg);
    }

    public static Sector toEntity(SectorConMiembrosDTO sectorDTO, Organizacion unaOrg) {
        Sector sectorEntity = null;
        try {
            sectorEntity = new Sector(sectorDTO.getNombre(), unaOrg);
            for(MiembroCSVDTO miembroDTO : sectorDTO.getMiembros()) {
                sectorEntity.agregarMiembro(MiembrosMapper.toEntity(miembroDTO));
            }
        } catch (MiembroException | SectorException e) {
            e.printStackTrace();
        }

        return sectorEntity;
    }
}
