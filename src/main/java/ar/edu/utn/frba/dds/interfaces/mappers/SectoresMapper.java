package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.exceptions.SectorException;
import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.exceptions.MiembroException;
import ar.edu.utn.frba.dds.interfaces.input.SectorConMiembrosDTO;
import ar.edu.utn.frba.dds.interfaces.input.SectorDTO;
import ar.edu.utn.frba.dds.interfaces.input.csv.MiembroCSVDTO;

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
