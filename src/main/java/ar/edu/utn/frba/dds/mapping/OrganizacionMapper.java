package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.lugares.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrganizacionMapper {
    public static void map(JSONObject organizacionDTO, Organizacion organizacion){
        JSONObject ubicacionGeograficaDTO = organizacionDTO.optJSONObject("ubicacionGeografica");
        JSONObject coodenadaDTO = ubicacionGeograficaDTO.optJSONObject("coordenada");
        JSONArray sectoresDTO = organizacionDTO.optJSONArray("sectores");
        JSONArray medicionesDTO = organizacionDTO.optJSONArray("mediciones");
        JSONArray trayectosDTO = organizacionDTO.optJSONArray("trayectos");

        UbicacionGeografica ubicacionGeografica = new UbicacionGeografica(
                ubicacionGeograficaDTO.optString("localidad"),
                new Coordenada(coodenadaDTO.optFloat("latitud"), coodenadaDTO.optFloat("longitud"))
        );

        organizacion.setRazonSocial(organizacionDTO.optString("razonSocial"));
        organizacion.setTipo(TipoDeOrganizacionEnum.valueOf(organizacionDTO.optString("tipoOrganizacion")));
        organizacion.setUbicacion(ubicacionGeografica);
        organizacion.setClasificacionOrganizacion( new ClasificacionOrganizacion(organizacionDTO.optString("clasificacionOrganizacion")));

    }
}
