package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.interfaces.input.json.UbicacionJSONDTO;

public class UbicacionMapper {

    public static UbicacionGeografica toEntity(UbicacionJSONDTO ubicacionDTO) {
        Direccion direccion;
        Coordenada coordenadas;
        if(ubicacionDTO.getDireccion() == null) {
            throw new RuntimeException("FUCKING DIRECCION"); //TODO
        } else {
            direccion = new Direccion(
                    ubicacionDTO.getDireccion().getPais(),
                    ubicacionDTO.getDireccion().getProvincia(),
                    ubicacionDTO.getDireccion().getMunicipio(),
                    ubicacionDTO.getDireccion().getLocalidad(),
                    ubicacionDTO.getDireccion().getCalle(),
                    ubicacionDTO.getDireccion().getNumero()
            );
        }
        if(ubicacionDTO.getCoordenadas() == null) {
            throw new RuntimeException("FUCKING COORDENADAS"); //TODO
        } else {
            coordenadas = new Coordenada(
                    ubicacionDTO.getCoordenadas().getLatitud(),
                    ubicacionDTO.getCoordenadas().getLongitud()
            );
        }

        return new UbicacionGeografica(direccion, coordenadas);
    }
}
