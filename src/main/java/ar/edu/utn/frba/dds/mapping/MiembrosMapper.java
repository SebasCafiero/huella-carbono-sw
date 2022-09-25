package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.mihuella.dto.MiembroJSONDTO;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;

import java.util.Set;

public class MiembrosMapper {
    private static Repositorio<Miembro> repositorio;

    public static Miembro toEntity(MiembroJSONDTO miembroDTO){
        return new Miembro(
                miembroDTO.nombre,
                miembroDTO.apellido,
                TipoDeDocumento.valueOf(miembroDTO.tipoDocumento.toUpperCase()),
                miembroDTO.documento
        );
    }
}
