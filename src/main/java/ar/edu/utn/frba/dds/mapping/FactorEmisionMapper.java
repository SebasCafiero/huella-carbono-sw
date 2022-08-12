package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import org.json.JSONObject;


public class FactorEmisionMapper {
    public static void map(JSONObject factorEmisionDTO, FactorEmision factorEmision){
        JSONObject categoriaDTO = factorEmisionDTO.optJSONObject("categoria");
        Categoria categoria = new Categoria(
                categoriaDTO.optInt("Id"),
                categoriaDTO.optString("actividad"),
                categoriaDTO.optString("tipoConsumo")
        );

        factorEmision.setCategoria(categoria);
        factorEmision.setUnidad(factorEmisionDTO.optString("unidad"));
        factorEmision.setValor(factorEmisionDTO.optFloat("valor"));
    }
}
