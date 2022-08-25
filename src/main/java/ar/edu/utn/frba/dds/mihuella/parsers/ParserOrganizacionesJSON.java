package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserOrganizacionesJSON {
    private static final Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);

    public static List<Organizacion> generarOrganizaciones(String archivo) throws IOException, ParseException {
        List<Organizacion> organizaciones = new ArrayList<>();
        JSONArray listaOrganizacionesJSON = new JSONArray(new JSONParser().parse(new FileReader(archivo)).toString());
//        for(Object orgJSON : listaOrganizacionesJSON) {
//            JSONObject = new JSONObject(orgJSON)
//        }
        for(int orgIndex = 0; orgIndex < listaOrganizacionesJSON.length(); orgIndex++){
            JSONObject unaOrgDTO = listaOrganizacionesJSON.optJSONObject(orgIndex);
            Organizacion unaOrg = OrganizacionMapper.toEntity(unaOrgDTO);

            repoMiembros.agregar(new Miembro("","", TipoDeDocumento.DNI,1));
            repoMiembros.agregar(new Miembro("","", TipoDeDocumento.DNI,2));
            repoMiembros.agregar(new Miembro("","", TipoDeDocumento.DNI,3));
            repoMiembros.agregar(new Miembro("","", TipoDeDocumento.DNI,4));

            organizaciones.add(unaOrg);
        }
        return organizaciones;
    }
}
