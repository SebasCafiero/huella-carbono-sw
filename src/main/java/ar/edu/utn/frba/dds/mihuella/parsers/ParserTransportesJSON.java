package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.mapping.TransportesMapper;
import ar.edu.utn.frba.dds.mihuella.dto.TransporteJSONDTO;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserTransportesJSON {

    private static final Repositorio<MedioDeTransporte> repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);

    public static List<MedioDeTransporte> generarTransportes(String archivo) throws FileNotFoundException {
        List<MedioDeTransporte> mediosDeTransporte = new ArrayList<>();

        Type listType = new TypeToken<List<TransporteJSONDTO>>(){}.getType();
        List<TransporteJSONDTO> transportesDTO = new Gson().fromJson(new FileReader(archivo),listType);

        System.out.println("\nCarga de Transportes2:\n" + new Gson().toJson(transportesDTO, listType) + "\n");

        for(TransporteJSONDTO transporteDTO : transportesDTO){
            MedioDeTransporte unTransporte = TransportesMapper.toEntity(transporteDTO);
            mediosDeTransporte.add(unTransporte);
            repoMedios.agregar(unTransporte);
            System.out.println("Agregado " + unTransporte + "\n");
        }

        return mediosDeTransporte;
    }
}
