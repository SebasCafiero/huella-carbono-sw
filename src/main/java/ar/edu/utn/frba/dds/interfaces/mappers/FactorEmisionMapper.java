package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.medibles.Categoria;
import ar.edu.utn.frba.dds.entities.medibles.FactorEmision;
import ar.edu.utn.frba.dds.interfaces.input.csv.FactorEmisionCSVDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.CategoriaJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.FactorEmisionJSONDTO;

import java.util.AbstractMap;
import java.util.Map;


public class FactorEmisionMapper {

    public static FactorEmision toEntity(FactorEmisionJSONDTO dto) {
        return new FactorEmision(
                new Categoria(dto.getCategoria().getActividad(), dto.getCategoria().getTipoConsumo()),
                dto.getUnidad(),
                dto.getValor()
        );
    }

    public static Map.Entry<String, Float> toEntry(FactorEmisionCSVDTO factor) {
        String key = factor.getActividad() + " -> " +
                factor.getTipoConsumo() + " : " +
                factor.getUnidad();

        return new AbstractMap.SimpleEntry<>(key, factor.getValor());
    }
}
