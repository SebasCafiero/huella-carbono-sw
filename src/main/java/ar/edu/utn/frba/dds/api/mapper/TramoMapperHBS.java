package ar.edu.utn.frba.dds.api.mapper;

import ar.edu.utn.frba.dds.api.dto.TramoHBS;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;

public class TramoMapperHBS {
    public static TramoHBS toDTO(Tramo tramo) {
        Integer idParadaInicial = null;
        Integer idParadaFinal = null;
        if(tramo.getMedioDeTransporte() instanceof TransportePublico) { //todo check busqueda de idParada
            idParadaInicial = ((TransportePublico) tramo.getMedioDeTransporte()).buscarParada(tramo.getUbicacionInicial().getCoordenada()).getId();
            idParadaFinal = ((TransportePublico) tramo.getMedioDeTransporte()).buscarParada(tramo.getUbicacionFinal().getCoordenada()).getId();
        }

        TramoHBS tramoDTO = new TramoHBS();
        tramoDTO.setId(tramo.getId());
        tramoDTO.setTransporte(TransporteMapperHBS.toDTOLazy(tramo.getMedioDeTransporte()));
        tramoDTO.setUbicacionInicial(UbicacionMapperHBS.toDTO(tramo.getUbicacionInicial()));
        tramoDTO.setUbicacionFinal(UbicacionMapperHBS.toDTO(tramo.getUbicacionFinal()));
        tramoDTO.setIdParadaInicial(idParadaInicial);
        tramoDTO.setIdParadaFinal(idParadaFinal);

        return tramoDTO;
    }

    public static TramoHBS toDTOLazy(Tramo tramo) {
        TramoHBS tramoDTO = new TramoHBS();
        tramoDTO.setTransporte(TransporteMapperHBS.toDTOLazy(tramo.getMedioDeTransporte()));
        return tramoDTO;
    }
}