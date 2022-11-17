package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.interfaces.input.json.CategoriaJSONDTO;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaJSONDTO categoriaDTO) {
        Categoria categoria = new Categoria(
                categoriaDTO.actividad,
                categoriaDTO.tipoConsumo
        );
    return categoria;
    }
}
