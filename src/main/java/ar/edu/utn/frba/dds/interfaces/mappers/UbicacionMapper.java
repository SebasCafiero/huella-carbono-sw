package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.interfaces.input.json.CoordenadaJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.DireccionJSONDTO;
import ar.edu.utn.frba.dds.interfaces.input.json.UbicacionJSONDTO;

public class UbicacionMapper {

    public static UbicacionGeografica toEntity(UbicacionJSONDTO dto) {
        Direccion direccion;
        Coordenada coordenadas;
        if(dto.getDireccion() == null) {
            throw new RuntimeException("FUCKING DIRECCION"); //TODO
        } else {
            direccion = new Direccion(
                    dto.getDireccion().getPais(),
                    dto.getDireccion().getProvincia(),
                    dto.getDireccion().getMunicipio(),
                    dto.getDireccion().getLocalidad(),
                    dto.getDireccion().getCalle(),
                    dto.getDireccion().getNumero()
            );
        }
        if(dto.getCoordenadas() == null) {
            throw new RuntimeException("FUCKING COORDENADAS"); //TODO
        } else {
            coordenadas = new Coordenada(
                    dto.getCoordenadas().getLatitud(),
                    dto.getCoordenadas().getLongitud()
            );
        }

        return new UbicacionGeografica(direccion, coordenadas);
    }

    public static UbicacionJSONDTO toDTO(UbicacionGeografica entity) {
        DireccionJSONDTO direccionDTO = null;
        CoordenadaJSONDTO coordenadasDTO = null;
        if(entity.getDireccion() != null) {
            direccionDTO = new DireccionJSONDTO();
            direccionDTO.setCalle(entity.getDireccion().getCalle());
            direccionDTO.setNumero(entity.getDireccion().getNumero());
            direccionDTO.setLocalidad(entity.getDireccion().getLocalidad());
            direccionDTO.setMunicipio(entity.getDireccion().getMunicipio().getNombre());
            direccionDTO.setProvincia(entity.getDireccion().getMunicipio().getProvincia().getNombre());
            direccionDTO.setPais(entity.getDireccion().getMunicipio().getProvincia().getNombrePais());
        }
        if(entity.getCoordenada() != null) {
            coordenadasDTO = new CoordenadaJSONDTO();
            coordenadasDTO.setLatitud(entity.getCoordenada().getLatitud());
            coordenadasDTO.setLongitud(entity.getCoordenada().getLongitud());
        }

        return new UbicacionJSONDTO(direccionDTO, coordenadasDTO);
    }
}
