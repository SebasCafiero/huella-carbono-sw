package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import com.google.gson.Gson;
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


    public static Organizacion toEntity(JSONObject organizacionDTO) {
        OrganizacionJSONDTO orgDTO = new Gson().fromJson(organizacionDTO.toString(), OrganizacionJSONDTO.class);
        Organizacion unaOrg = new Organizacion(
                orgDTO.organizacion,
                TipoDeOrganizacionEnum.valueOf(orgDTO.tipo), //case sensitive!
                new ClasificacionOrganizacion(orgDTO.clasificacion),
                UbicacionMapper.toEntity(orgDTO.ubicacion)
        );

        for(OrganizacionJSONDTO.SectorJSONDTO sectorDTO : orgDTO.sectores) {
            Sector unSector;
            try {
                unSector = new Sector(sectorDTO.nombre, unaOrg);
                unSector.agregarMiembro(new Miembro("","", TipoDeDocumento.DNI,1));
                unSector.agregarMiembro(new Miembro("","", TipoDeDocumento.DNI,2));
            } catch (SectorException e) {
                e.printStackTrace();
            } catch (MiembroException e) {
                e.printStackTrace();
            }

        }
        
        System.out.println(unaOrg.toString());
        return unaOrg;
    }
}
