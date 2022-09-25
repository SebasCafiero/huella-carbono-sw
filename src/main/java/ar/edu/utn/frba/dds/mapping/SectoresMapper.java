package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.MiembroException;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.mihuella.dto.MiembroJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SectoresMapper {

    public static Sector toEntity(OrganizacionJSONDTO.SectorJSONDTO sectorDTO, Organizacion unaOrg) {

            Sector sectorEntity = null;
            try {
                sectorEntity = new Sector(sectorDTO.nombre, unaOrg);
                for(MiembroJSONDTO miembroDTO : sectorDTO.miembros){
                    try{
                        sectorEntity.agregarMiembro(MiembrosMapper.toEntity(miembroDTO));
                    }
                    catch (MiembroException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SectorException e) {
                e.printStackTrace();
            }


        return sectorEntity;
    }
}
