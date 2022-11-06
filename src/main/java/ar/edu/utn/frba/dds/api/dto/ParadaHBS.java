package ar.edu.utn.frba.dds.api.dto;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParadaHBS {
    private Integer id;
    private UbicacionHBS ubicacion;

    public ParadaHBS() {
    }

    public Integer getId() {
        return id;
    }

    public UbicacionHBS getUbicacion() {
        return ubicacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUbicacion(UbicacionHBS ubicacion) {
        this.ubicacion = ubicacion;
    }
}