package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.mapping.CategoriaMapper;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionJSONDTO;
import com.google.gson.Gson;


public class ParserFactoresJSON {
    public static FactorEmision generarFactor(String json) {

        System.out.println("Request: "+json);
        FactorEmisionJSONDTO factorDTO = new Gson().fromJson(json, FactorEmisionJSONDTO.class);
        return new FactorEmision(
                CategoriaMapper.toEntity(factorDTO.categoria),
                factorDTO.unidad,
                factorDTO.valor);
    }
}
