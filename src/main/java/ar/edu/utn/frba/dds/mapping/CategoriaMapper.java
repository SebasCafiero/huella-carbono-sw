package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.mihuella.dto.CategoriaJSONDTO;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaJSONDTO categoriaDTO) {
        Categoria categoria = new Categoria(
                categoriaDTO.actividad,
                categoriaDTO.tipoConsumo
        );
    return categoria;
    }
}
