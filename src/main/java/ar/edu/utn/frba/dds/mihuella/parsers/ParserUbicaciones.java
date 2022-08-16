package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.mapping.UbicacionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.UbicacionCSVDTO;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ParserUbicaciones {
    public List<UbicacionGeografica> generarUbicaciones(String arch) throws IOException {
        List<UbicacionCSVDTO> ubicacionesCSVDTO = new CsvToBeanBuilder(new FileReader(arch))
                .withType(UbicacionCSVDTO.class)
                .build()
                .parse();
        return ubicacionesCSVDTO.stream().map(u-> UbicacionMapper.toEntity(u)).collect(Collectors.toList());
    }
}
