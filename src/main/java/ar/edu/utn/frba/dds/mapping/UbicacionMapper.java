package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Municipio;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.mihuella.dto.UbicacionCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.UbicacionJSONDTO;
import com.google.gson.Gson;
import org.json.JSONObject;

public class UbicacionMapper {

    public static UbicacionGeografica toEntity(JSONObject ubicacionDTO) {
        //org.json.JSONObject -> com.google.gson.JsonObject (serializo y parseo con string)
        return toEntity(new Gson().fromJson(ubicacionDTO.toString(), UbicacionJSONDTO.class));
    }

    public static UbicacionGeografica toEntity(UbicacionJSONDTO ubicacionDTO) {
        Direccion direccion;
        Coordenada coordenadas;
        if(ubicacionDTO.direccion == null) {
            throw new RuntimeException("FUCKING DIRECCION"); //TODO
        } else {
            direccion = new Direccion(
                    ubicacionDTO.direccion.pais,
                    ubicacionDTO.direccion.provincia,
                    ubicacionDTO.direccion.municipio,
                    ubicacionDTO.direccion.localidad,
                    ubicacionDTO.direccion.calle,
                    ubicacionDTO.direccion.numero
            );
        }
        if(ubicacionDTO.coordenadas == null) {
            throw new RuntimeException("FUCKING COORDENADAS"); //TODO
        } else {
            coordenadas = new Coordenada(
                    ubicacionDTO.coordenadas.latitud,
                    ubicacionDTO.coordenadas.longitud
            );
        }

        System.out.println("DIR: " + direccion);
        System.out.println("COR: " + coordenadas);
        return new UbicacionGeografica(direccion, coordenadas);
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

    public static Direccion mapDireccion(Coordenada unaCoordenada) { //TODO
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

    public static Coordenada mapCoordenada(Direccion unaDireccion) { //TODO
        Float latitud = 10F;
        Float longitud = 15F;

        Coordenada coordenada = new Coordenada(latitud, longitud);
        return coordenada;
    }
}
