package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mihuella.dto.TransporteJSONDTO;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ParserTransportesJSON {

    public static List<TransporteJSONDTO> generarTransportes(String archivo) throws FileNotFoundException {
        Type listType = new TypeToken<List<TransporteJSONDTO>>(){}.getType();
        return new Gson().fromJson(new FileReader(archivo),listType);
    }
}
