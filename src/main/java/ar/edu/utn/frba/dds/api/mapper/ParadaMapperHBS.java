package ar.edu.utn.frba.dds.api.mapper;

import ar.edu.utn.frba.dds.api.dto.ParadaHBS;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.transportes.Parada;

import java.util.HashMap;
import java.util.Map;

public class ParadaMapperHBS {

    public static ParadaHBS toDTO(Parada parada) {
        ParadaHBS paradaDTO = new ParadaHBS();
        paradaDTO.setId(parada.getId());
        paradaDTO.setUbicacion(UbicacionMapperHBS.toDTO(parada.getUbicacion()));
        return paradaDTO;
    }
}