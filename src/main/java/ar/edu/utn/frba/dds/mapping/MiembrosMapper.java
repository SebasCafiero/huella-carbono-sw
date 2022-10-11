package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.mihuella.dto.MiembroCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.MiembroJSONDTO;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.Set;

public class MiembrosMapper {
    public static Miembro toEntity(MiembroCSVDTO miembroDTO){
        return new Miembro(miembroDTO.getNombre(), miembroDTO.getApellido(),
                TipoDeDocumento.valueOf(miembroDTO.getTipoDocumento().toUpperCase()),
                Integer.parseInt(miembroDTO.getDocumento())
        );
    }
}
