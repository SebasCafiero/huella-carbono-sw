package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrganizacionMapper {
    public static void map(JSONObject organizacionDTO, Organizacion organizacion){
        JSONObject ubicacionGeograficaDTO = organizacionDTO.optJSONObject("ubicacionGeografica");
        JSONObject coodenadaDTO = ubicacionGeograficaDTO.optJSONObject("coordenada");
        JSONArray sectoresDTO = organizacionDTO.optJSONArray("sectores");
        JSONArray medicionesDTO = organizacionDTO.optJSONArray("mediciones");
        JSONArray trayectosDTO = organizacionDTO.optJSONArray("trayectos");

        List<Trayecto> trayectos = new ArrayList<>();

        UbicacionGeografica ubicacionGeografica = new UbicacionGeografica(
//                ubicacionGeograficaDTO.optString("localidad"),
                new Coordenada(coodenadaDTO.optFloat("latitud"), coodenadaDTO.optFloat("longitud"))
        );

        TrayectosMapper.map(trayectosDTO, trayectos);

        organizacion.setRazonSocial(organizacionDTO.optString("razonSocial"));
        organizacion.setTipo(TipoDeOrganizacionEnum.valueOf(organizacionDTO.optString("tipoOrganizacion")));
        organizacion.setUbicacion(ubicacionGeografica);
        organizacion.setClasificacionOrganizacion( new ClasificacionOrganizacion(organizacionDTO.optString("clasificacionOrganizacion")));
    }
}
