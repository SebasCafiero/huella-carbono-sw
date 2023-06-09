package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.UbicacionHBS;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;

public class UbicacionMapperHBS {
    public static UbicacionHBS toDTO(UbicacionGeografica ubicacion) {
        UbicacionHBS ubicacionDTO = new UbicacionHBS();
        ubicacionDTO.setProvincia(ubicacion.getDireccion().getMunicipio().getProvincia().getNombre());
        ubicacionDTO.setMunicipio(ubicacion.getDireccion().getMunicipio().getNombre());
        ubicacionDTO.setLocalidad(ubicacion.getDireccion().getLocalidad());
        ubicacionDTO.setCalle(ubicacion.getDireccion().getCalle());
        ubicacionDTO.setNumero(ubicacion.getDireccion().getNumero());
        ubicacionDTO.setLatitud(ubicacion.getCoordenada().getLatitud());
        ubicacionDTO.setLongitud(ubicacion.getCoordenada().getLongitud());
        return ubicacionDTO;
    }
}