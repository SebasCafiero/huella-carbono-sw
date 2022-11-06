package ar.edu.utn.frba.dds.api.mapper;

import ar.edu.utn.frba.dds.api.dto.ConsumoHBS;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.mediciones.Categoria;
import ar.edu.utn.frba.dds.entities.personas.Miembro;

import java.util.Map;

public class ConsumoMapperHBS {

    /*public static ConsumoHBS toDTO(Map.Entry consumoPorCriterio) {
        ConsumoHBS consumoDTO = new ConsumoHBS();
        consumoDTO.setConcepto(consumoPorCriterio.getKey().toString());
        consumoDTO.setConsumo(Float.parseFloat(consumoPorCriterio.getValue().toString()));
        return consumoDTO;
    }*/

    public static ConsumoHBS toDTO(Map.Entry<?, Float> consumoPorCriterio) {
        ConsumoHBS consumoDTO = new ConsumoHBS();
        consumoDTO.setConcepto(consumoPorCriterio.getKey().toString()); //todo check
        consumoDTO.setConsumo(consumoPorCriterio.getValue());
        return consumoDTO;
    }
}
