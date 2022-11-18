package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.ParadaHBS;
import ar.edu.utn.frba.dds.entities.transportes.Parada;

public class ParadaMapperHBS {

    public static ParadaHBS toDTO(Parada parada) {
        ParadaHBS paradaDTO = new ParadaHBS();
        paradaDTO.setId(parada.getId());
        paradaDTO.setUbicacion(UbicacionMapperHBS.toDTO(parada.getUbicacion()));
        return paradaDTO;
    }
}