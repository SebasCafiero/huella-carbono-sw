package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.interfaces.input.json.MiembroResponse;

public class MiembrosMapper {
    public static Miembro toEntity(MiembroResponse miembroDTO){
        return new Miembro(miembroDTO.getNombre(), miembroDTO.getApellido(),
                TipoDeDocumento.valueOf(miembroDTO.getTipoDocumento().toUpperCase()),
                Integer.parseInt(miembroDTO.getDocumento())
        );
    }

    public static MiembroResponse toResponse(Miembro miembro) {
        MiembroResponse dto = new MiembroResponse();
        dto.setNombre(miembro.getNombre());
        dto.setApellido(miembro.getApellido());
        dto.setTipoDocumento(miembro.getTipoDeDocumento().name());
        dto.setDocumento(miembro.getNroDocumento().toString());

        return dto;
    }
}
