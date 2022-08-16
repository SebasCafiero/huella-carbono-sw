package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Municipio;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.mihuella.dto.UbicacionCSVDTO;
import org.json.JSONObject;

public class UbicacionMapper {

    public static UbicacionGeografica toEntity(JSONObject ubicacionDTO) {
        return new UbicacionGeografica(
                ubicacionDTO.optString("pais"),
                ubicacionDTO.optString("provincia"),
                ubicacionDTO.optString("municipio"),
                ubicacionDTO.optString("localidad"),
                ubicacionDTO.optString("calle"),
                ubicacionDTO.optInt("numero"),
                new Coordenada(ubicacionDTO.optFloat("latitud"), ubicacionDTO.optFloat("longitud"))
        );
    }

    public static UbicacionGeografica toEntity(UbicacionCSVDTO ubicacionDTO) {
        return new UbicacionGeografica(
                ubicacionDTO.getPais(),
                ubicacionDTO.getProvincia(),
                ubicacionDTO.getMunicipio(),
                ubicacionDTO.getLocalidad(),
                "",
                0,
                new Coordenada(ubicacionDTO.getLatitudBase(), ubicacionDTO.getLongitudBase())
        );
    }


    public static UbicacionGeografica toEntity(Coordenada coordenadaDTO) {
        return new UbicacionGeografica(mapDireccion(coordenadaDTO), coordenadaDTO);
    }

    public static UbicacionGeografica toEntity(Direccion direccionDTO) {
        return new UbicacionGeografica(direccionDTO, mapCoordenada(direccionDTO));
    }

    public static Direccion mapDireccion(Coordenada unaCoordenada) {
        String nombrePais = "Argentina";
        String nombreProvincia = "Buenos Aires";
        String nombreMunicipio = "Avellaneda";
        String nombreLocalidad = "Dock Sud";
        String calle = "callecita";
        Integer numero = 100;
        Municipio municipio = new Municipio(nombreMunicipio, nombreProvincia, nombrePais);
        Direccion direccion = new Direccion(municipio, nombreLocalidad, calle, numero);
        return direccion;

    }

    public static Coordenada mapCoordenada(Direccion unaDireccion) {
        Float latitud = 10F;
        Float longitud = 15F;

        Coordenada coordenada = new Coordenada(latitud, longitud);
        return coordenada;
    }
}
