package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionJSONDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ParserOrganizacionesJSON {

    public static List<OrganizacionJSONDTO> generarOrganizaciones(String archivo) throws IOException {
        Type listType = new TypeToken<List<OrganizacionJSONDTO>>() {}.getType();
        return new Gson().fromJson(new FileReader(archivo), listType);
    }

    public static OrganizacionJSONDTO generarOrganizacion(String json) {
        return new Gson().fromJson(json,OrganizacionJSONDTO.class);
    }

}
