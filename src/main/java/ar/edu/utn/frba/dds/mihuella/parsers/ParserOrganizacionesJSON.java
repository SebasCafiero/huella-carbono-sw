package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserOrganizacionesJSON {
    private static final Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);

    public static List<Organizacion> generarOrganizaciones(String archivo) throws IOException, ParseException {
        List<Organizacion> organizaciones = new ArrayList<>();
//        JSONArray listaOrganizacionesJSON = new JSONArray(new JSONParser().parse(new FileReader(archivo)).toString());

        Type listType = new TypeToken<List<OrganizacionJSONDTO>>(){}.getType();
        List<OrganizacionJSONDTO> organizacionesDTO = new Gson().fromJson(new FileReader(archivo), listType);

        for(OrganizacionJSONDTO organizacionDTO : organizacionesDTO){
            Organizacion unaOrg = OrganizacionMapper.toEntity(organizacionDTO);
            for(Miembro unMiembro : unaOrg.getMiembros()){
                repoMiembros.agregar(unMiembro);
            }
            organizaciones.add(unaOrg);
        }


        /*for(Object orgJSON : listaOrganizacionesJSON) {
            JSONObject unaOrgDTO = (JSONObject) orgJSON;
            Organizacion unaOrg = OrganizacionMapper.toEntity(unaOrgDTO);

            for(Miembro unMiembro : unaOrg.getMiembros()){
                repoMiembros.agregar(unMiembro);
            }
            organizaciones.add(unaOrg);
        }*/

        /*for(int orgIndex = 0; orgIndex < listaOrganizacionesJSON.length(); orgIndex++){
            JSONObject unaOrgDTO = listaOrganizacionesJSON.optJSONObject(orgIndex);
            Organizacion unaOrg = OrganizacionMapper.toEntity(unaOrgDTO);
            for(Miembro unMiembro : unaOrg.getMiembros()){
                repoMiembros.agregar(unMiembro);
            }
            organizaciones.add(unaOrg);
        }*/
        return organizaciones;
    }
}