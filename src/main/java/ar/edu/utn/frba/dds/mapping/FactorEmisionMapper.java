package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionJSONDTO;



public class FactorEmisionMapper {

    public static FactorEmision toEntity(FactorEmisionJSONDTO factorEmisionDTO) {
        FactorEmision factorEmision = new FactorEmision(
                CategoriaMapper.toEntity(factorEmisionDTO.categoria),
                factorEmisionDTO.unidad,
                factorEmisionDTO.valor
        );
        return factorEmision;
    }
}
