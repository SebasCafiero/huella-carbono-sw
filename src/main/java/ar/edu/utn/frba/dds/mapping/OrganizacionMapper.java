package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.lugares.*;
import org.json.JSONObject;

public class OrganizacionMapper {
    public static void map(JSONObject organizacionDTO, Organizacion organizacion){
        organizacion.setRazonSocial(organizacionDTO.optString("razonSocial"));
        organizacion.setTipo(TipoDeOrganizacionEnum.valueOf(organizacionDTO.optString("tipoOrganizacion")));

        JSONObject ubicacionGeograficaDTO = organizacionDTO.getJSONObject("ubicacionGeografica");
        JSONObject coodenadaDTO = ubicacionGeograficaDTO.getJSONObject("coordenada");

        UbicacionGeografica ubicacionGeografica = new UbicacionGeografica(
                ubicacionGeograficaDTO.optString("localidad"),
                new Coordenada(coodenadaDTO.optFloat("latitud"), coodenadaDTO.optFloat("longitud"))
        );

        organizacion.setUbicacion(ubicacionGeografica);

        organizacion.setClasificacionOrganizacion( new ClasificacionOrganizacion(organizacionDTO.optString("clasificacionOrganizacion")));


    }
}
