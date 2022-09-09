package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.dto.MiembroJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class OrganizacionMapper {
    public static void map(JSONObject organizacionDTO, Organizacion organizacion){
        //Preparo toda la data
        JSONObject ubicacionGeograficaDTO = organizacionDTO.optJSONObject("ubicacionGeografica");
        JSONObject coodenadaDTO = ubicacionGeograficaDTO.optJSONObject("coordenada");
        JSONArray sectoresDTO = organizacionDTO.optJSONArray("sectores");
        JSONArray medicionesDTO = organizacionDTO.optJSONArray("mediciones");
        JSONArray trayectosDTO = organizacionDTO.optJSONArray("trayectos");

        List<Trayecto> trayectos = new ArrayList<>();
        TrayectosMapper.map(trayectosDTO, trayectos);

        Set<Sector> sectores = new HashSet<>();
        SectoresMapper.map(sectoresDTO, sectores);

        UbicacionGeografica ubicacionGeografica = new UbicacionGeografica(
                new Coordenada(coodenadaDTO.optFloat("latitud"), coodenadaDTO.optFloat("longitud"))
        );

        TrayectosMapper.map(trayectosDTO, trayectos);

        organizacion.setRazonSocial(organizacionDTO.optString("razonSocial"));
        organizacion.setTipo(TipoDeOrganizacionEnum.valueOf(organizacionDTO.optString("tipoOrganizacion")));
        organizacion.setUbicacion(ubicacionGeografica);
        organizacion.setClasificacionOrganizacion( new ClasificacionOrganizacion(organizacionDTO.optString("clasificacionOrganizacion")));
        organizacion.setSectores(sectores);
    }


    public static Organizacion toEntity(OrganizacionJSONDTO orgDTO) {
        Organizacion unaOrg = new Organizacion(
                orgDTO.organizacion,
                TipoDeOrganizacionEnum.valueOf(orgDTO.tipo.toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.clasificacion),
                UbicacionMapper.toEntity(orgDTO.ubicacion)
        );
        for(OrganizacionJSONDTO.SectorJSONDTO sectorDTO : orgDTO.sectores) {
            Sector unSector;
            try {
                unSector = new Sector(sectorDTO.nombre, unaOrg);
                for(MiembroJSONDTO unMiembro : sectorDTO.miembros){
                    unSector.agregarMiembro(new Miembro(
                            unMiembro.nombre,
                            unMiembro.apellido,
                            TipoDeDocumento.valueOf(unMiembro.tipoDocumento.toUpperCase()),
                            unMiembro.documento
                    ));
                }
            } catch (SectorException e) {
                e.printStackTrace();
            } catch (MiembroException e) {
                e.printStackTrace();
            }
        }

        System.out.println(unaOrg.toString());
        return unaOrg;
    }

    public static Organizacion toEntity(JSONObject organizacionDTO) {
        OrganizacionJSONDTO orgDTO = new Gson().fromJson(organizacionDTO.toString(), OrganizacionJSONDTO.class);
        Organizacion unaOrg = new Organizacion(
                orgDTO.organizacion,
                TipoDeOrganizacionEnum.valueOf(orgDTO.tipo.toUpperCase()),
                new ClasificacionOrganizacion(orgDTO.clasificacion),
                UbicacionMapper.toEntity(orgDTO.ubicacion)
        );

        for(OrganizacionJSONDTO.SectorJSONDTO sectorDTO : orgDTO.sectores) {
            Sector unSector;
            try {
                unSector = new Sector(sectorDTO.nombre, unaOrg);
                for(MiembroJSONDTO unMiembro : sectorDTO.miembros){
                    unSector.agregarMiembro(new Miembro(
                            unMiembro.nombre,
                            unMiembro.apellido,
                            TipoDeDocumento.valueOf(unMiembro.tipoDocumento.toUpperCase()),
                            unMiembro.documento
                    ));
                }
            } catch (SectorException e) {
                e.printStackTrace();
            } catch (MiembroException e) {
                e.printStackTrace();
            }

        }

        System.out.println(unaOrg.toString());
        return unaOrg;
    }

    public static Set<Organizacion> toSetOfEntity(Set<OrganizacionJSONDTO> organizacionesDTO) {
        Set<Organizacion> organizaciones = (Set<Organizacion>) new ArrayList<Organizacion>(); //esta bien esto??
        organizacionesDTO.forEach(organizacionJSONDTO -> {
            organizaciones.add(OrganizacionMapper.toEntity(organizacionJSONDTO));
        });

        return organizaciones;
    }

    public static List<Organizacion> toListOfEntity(List<OrganizacionJSONDTO> organizacinesDTO) {
        List<Organizacion> organizaciones = new ArrayList<>();
        organizacinesDTO.forEach(organizacionJSONDTO -> {
            organizaciones.add(OrganizacionMapper.toEntity(organizacionJSONDTO));
        });
        return organizaciones;
    }
}
