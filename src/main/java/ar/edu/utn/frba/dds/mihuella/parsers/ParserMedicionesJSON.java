package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mapping.MedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.MedicionJSONDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserMedicionesJSON implements ParserMediciones {
    @Override
    public List<Medicion> generarMediciones(String archivo) throws IOException {
        List<Medicion> mediciones = new ArrayList<>();

        Type listType = new TypeToken<List<MedicionJSONDTO>>(){}.getType();
        List<MedicionJSONDTO> medicionesDTO = new Gson().fromJson(new FileReader(archivo), listType);

        for(MedicionJSONDTO medicionDTO : medicionesDTO){
            Medicion unaMedicion = MedicionMapper.toEntity(medicionDTO);
            mediciones.add(unaMedicion);
        }
        return mediciones;
    }

    public Medicion generarMedicion(String json) throws IOException {
        MedicionJSONDTO medicionDTO = new Gson().fromJson(json, MedicionJSONDTO.class);
        Medicion unaMedicion = MedicionMapper.toEntity(medicionDTO);
        return unaMedicion;
    }
}
