package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TramoHBS;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TransporteHBS;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TrayectoHBS;
import ar.edu.utn.frba.dds.entities.medibles.Trayecto;
import spark.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public static TrayectoHBS toDTOEditable(Trayecto trayecto, Request request) {
        TrayectoHBS trayectoDTO = new TrayectoHBS();

        Periodo periodo = request.queryParams("fecha") == null ? trayecto.getPeriodo()
                : parsearPeriodo(request.queryParams("fecha"));
        if(periodo.getPeriodicidad() == 'M')
            trayectoDTO.setFecha(periodo.getMes() + "/" + periodo.getAnio().toString());
        else
            trayectoDTO.setFecha(periodo.getAnio().toString());

        trayectoDTO.setMiembros(trayecto.getMiembros().stream().map(MiembroMapperHBS::toDTO).collect(Collectors.toList()));
        trayectoDTO.setId(trayecto.getId());

        return trayectoDTO;
    }

    private static Periodo parsearPeriodo(String input) {
        Periodo periodo;
        if(input.matches("\\d+")) {
            periodo = new Periodo(Integer.parseInt(input));
        } else if(input.matches("\\d+/\\d+")) {
            String[] fecha = input.split("/"); //todo validar
            periodo = new Periodo(Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
        } else {
            LocalDate fecha = LocalDate.now();
            periodo = new Periodo(fecha.getYear(), fecha.getMonthValue());
        }
        return periodo;
    }
}

