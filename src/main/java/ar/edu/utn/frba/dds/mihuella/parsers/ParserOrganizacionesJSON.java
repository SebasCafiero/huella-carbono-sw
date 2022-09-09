package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.mapping.MedicionMapper;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.MedicionJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserOrganizacionesJSON {
    private static final Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);

    public static List<Organizacion> generarOrganizaciones(String archivo) throws IOException, ParseException {
        List<Organizacion> organizaciones = new ArrayList<>();
        Type listType = new TypeToken<List<OrganizacionJSONDTO>>(){}.getType();
        List<OrganizacionJSONDTO> organizacionesDTO = new Gson().fromJson(new FileReader(archivo), listType);

        for(OrganizacionJSONDTO organizacionDTO : organizacionesDTO){
            Organizacion unaOrg = OrganizacionMapper.toEntity(organizacionDTO);
            for(Miembro unMiembro : unaOrg.getMiembros()){
                repoMiembros.agregar(unMiembro);
            }
            organizaciones.add(unaOrg);
        }
        return organizaciones;
    }

    public static Organizacion generarOrganizacion(String json) {
        OrganizacionJSONDTO organizacionnDTO = new Gson().fromJson(json,OrganizacionJSONDTO.class);
        Organizacion unaOrganizacion = OrganizacionMapper.toEntity(organizacionnDTO);
        return unaOrganizacion;
    }
}
