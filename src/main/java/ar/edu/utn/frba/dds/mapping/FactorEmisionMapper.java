package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionJSONDTO;

import java.util.AbstractMap;
import java.util.Map;


public class FactorEmisionMapper {

    public static FactorEmision toEntity(FactorEmisionJSONDTO factorEmisionDTO) {
        FactorEmision factorEmision = new FactorEmision(
                CategoriaMapper.toEntity(factorEmisionDTO.categoria),
                factorEmisionDTO.unidad,
                factorEmisionDTO.valor
        );
        return factorEmision;
    }

    public static Map.Entry<String, Float> toEntry(FactorEmisionCSVDTO factor) {
        String key = factor.getActividad() + " -> " +
                factor.getTipoConsumo() + " : " +
                factor.getUnidad();

        return new AbstractMap.SimpleEntry<>(key, factor.getValor());
    }
}
