package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.TrayectoHBS;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;

import java.util.stream.Collectors;

public class TrayectoMapperHBS {

    public static TrayectoHBS toDTO(Trayecto trayecto) {
        TrayectoHBS trayectoDTO = new TrayectoHBS();
        trayectoDTO.setId(trayecto.getId());
        if(trayecto.getPeriodo().getPeriodicidad() == 'M')
            trayectoDTO.setFecha(trayecto.getPeriodo().getMes(), trayecto.getPeriodo().getAnio());
        else
            trayectoDTO.setFecha(trayecto.getPeriodo().getAnio().toString());
        trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTO).collect(Collectors.toList()));
        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        return trayectoDTO;
    }

    public static TrayectoHBS toDTOLazy(Trayecto trayecto) {
        TrayectoHBS trayectoDTO = new TrayectoHBS();
        trayectoDTO.setId(trayecto.getId());
        if(trayecto.getPeriodo().getPeriodicidad() == 'M')
            trayectoDTO.setFecha(trayecto.getPeriodo().getMes(), trayecto.getPeriodo().getAnio());
        else
            trayectoDTO.setFecha(trayecto.getPeriodo().getAnio().toString());
        trayectoDTO.setTramos(trayecto.getTramos().stream().map(t -> TramoMapperHBS.toDTOLazy(t.getMedioDeTransporte())).collect(Collectors.toList()));
        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTOLazy).collect(Collectors.toList()));
        return trayectoDTO;
    }

    public static TrayectoHBS toDTOEditable(Trayecto trayecto) {
        TrayectoHBS trayectoDTO = toDTO(trayecto);
        trayectoDTO.setTramos(trayecto.getTramos().stream().map(TramoMapperHBS::toDTO).collect(Collectors.toList()));
        return trayectoDTO;
    }

}

