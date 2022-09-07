package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionJSONDTO;
import org.json.JSONObject;


public class FactorEmisionMapper {
    public static FactorEmision toEntity(JSONObject factorEmisionDTO){
        FactorEmision factorEmision = new FactorEmision();
        JSONObject categoriaDTO = factorEmisionDTO.optJSONObject("categoria");
        Categoria categoria = new Categoria(
                categoriaDTO.optInt("Id"),
                categoriaDTO.optString("actividad"),
                categoriaDTO.optString("tipoConsumo")
        );

        factorEmision.setCategoria(categoria);
        factorEmision.setUnidad(factorEmisionDTO.optString("unidad"));
        factorEmision.setValor(factorEmisionDTO.optFloat("valor"));

        return factorEmision;
    }

    public static FactorEmision toEntity(FactorEmisionJSONDTO factorEmisionDTO) {
        FactorEmision factorEmision = new FactorEmision(
                CategoriaMapper.toEntity(factorEmisionDTO.categoria),
                factorEmisionDTO.unidad,
                factorEmisionDTO.valor
        );
        return factorEmision;
    }
}
