package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.ConsumoHBS;

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
