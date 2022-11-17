package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.MiembroHBS;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

public class MiembroMapperHBS {

    public static MiembroHBS toDTO(Miembro miembro) {
        MiembroHBS miembroDTO = new MiembroHBS();
        miembroDTO.setNombre(miembro.getNombre());
        miembroDTO.setApellido(miembro.getApellido());
        miembroDTO.setTipoDeDocumento(miembro.getTipoDeDocumento().toString());
        miembroDTO.setNroDocumento(miembro.getNroDocumento());
        miembroDTO.setId(miembro.getId());
        return miembroDTO;
    }

    public static MiembroHBS toDTOLazy(Miembro miembro) {
        MiembroHBS miembroDTO = new MiembroHBS();
        miembroDTO.setNombre(miembro.getNombre());
        miembroDTO.setApellido(miembro.getApellido());
        return miembroDTO;
    }
}