package ar.edu.utn.frba.dds.api.mapper;

import ar.edu.utn.frba.dds.api.dto.TrayectoHBS;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TrayectoMapperHBS {

    public static TrayectoHBS toDTO(Trayecto trayecto) {
        TrayectoHBS trayectoDTO = new TrayectoHBS();
        trayectoDTO.setId(trayecto.getId());
        trayectoDTO.setMes(trayecto.getPeriodo().getMes());
        trayectoDTO.setAño(trayecto.getPeriodo().getAnio());
        trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTO).collect(Collectors.toList()));
        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        return trayectoDTO;
    }

    public static TrayectoHBS toDTOLazy(Trayecto trayecto) {
        TrayectoHBS trayectoDTO = new TrayectoHBS();
        trayectoDTO.setId(trayecto.getId());
        trayectoDTO.setMes(trayecto.getPeriodo().getMes());
        trayectoDTO.setAño(trayecto.getPeriodo().getAnio());
        trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTOLazy).collect(Collectors.toList()));
        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTOLazy).collect(Collectors.toList()));
        return trayectoDTO;
    }
}

