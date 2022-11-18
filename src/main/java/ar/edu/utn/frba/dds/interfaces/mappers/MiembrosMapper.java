package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.interfaces.input.csv.MiembroCSVDTO;

public class MiembrosMapper {
    public static Miembro toEntity(MiembroCSVDTO miembroDTO){
        return new Miembro(miembroDTO.getNombre(), miembroDTO.getApellido(),
                TipoDeDocumento.valueOf(miembroDTO.getTipoDocumento().toUpperCase()),
                Integer.parseInt(miembroDTO.getDocumento())
        );
    }
}
