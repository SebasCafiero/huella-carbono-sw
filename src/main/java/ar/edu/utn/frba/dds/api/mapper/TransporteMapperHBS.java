package ar.edu.utn.frba.dds.api.mapper;

import ar.edu.utn.frba.dds.api.dto.ParadaHBS;
import ar.edu.utn.frba.dds.api.dto.TransporteHBS;
import ar.edu.utn.frba.dds.entities.transportes.*;

import java.util.List;
import java.util.stream.Collectors;

public class TransporteMapperHBS {

    public static TransporteHBS toDTO(MedioDeTransporte transporte) {
        List<ParadaHBS> paradas = null;
        Boolean esPublico = false;
        if(transporte instanceof TransportePublico) {
            paradas = ((TransportePublico) transporte).getParadas().stream().map(ParadaMapperHBS::toDTO).collect(Collectors.toList());
            esPublico = true;
        }

        TransporteHBS transporteDTO = new TransporteHBS();
        transporteDTO.setId(transporte.getId());
        transporteDTO.setInfo(transporte.getClasificacion());
        transporteDTO.setParadas(paradas);
        transporteDTO.setEsPublico(esPublico);

        return transporteDTO;
    }

    public static TransporteHBS toDTOLazy(MedioDeTransporte transporte) {
        TransporteHBS transporteDTO = new TransporteHBS();
        transporteDTO.setId(transporte.getId());
        transporteDTO.setInfo(transporte.getClasificacion());
        return transporteDTO;
    }
}