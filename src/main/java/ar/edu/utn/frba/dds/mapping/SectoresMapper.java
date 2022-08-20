package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class SectoresMapper {

    private static Repositorio<Organizacion> repositorio;
    public static void map(JSONArray sectoresDTO, Set<Sector> sectores){
        sectoresDTO.forEach(itemSector -> {

            JSONObject sectorDTO = (JSONObject) itemSector;
            int idOrg = sectorDTO.optInt("organizacion");
            JSONArray idMiembrosDTO = sectorDTO.optJSONArray("miembros");
            String nombre = sectorDTO.optString("nombre");

            Set<Miembro> miembros = new HashSet<>();

            idMiembrosDTO.forEach( itemMiembro ->{
                int id = Integer.parseInt((String) itemMiembro);
                MiembrosMapper.map(id, miembros);
            });

            repositorio = FactoryRepositorio.get(Organizacion.class);
            Organizacion org = repositorio.buscar(idOrg);

            Sector sector = new Sector(nombre, org, miembros);
            sectores.add(sector);
        });
    }
}
