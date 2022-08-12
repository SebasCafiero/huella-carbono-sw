package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mihuella.MedicionCSVDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.mihuella.utils.MedicionCSVMapper;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ParserMedicionesCSV implements ParserMediciones {
    @Override
    public List<Medible> generarMediciones(String archivo) throws IOException {
        List<MedicionCSVDTO> mediciones = new CsvToBeanBuilder(new FileReader(archivo))
                .withType(MedicionCSVDTO.class)
                .build()
                .parse();

        return mediciones.stream().map(MedicionCSVMapper::toEntity).collect(Collectors.toList());
    }
}

/*
    String extensionArchivo = "";
    int index = archivo.lastIndexOf('.');
    if (index > 0)
        extensionArchivo = archivo.substring(index + 1);
    else throw new ArchivoSinExtensionException(archivo);

    FileReader fileDescriptor;
    try {
        fileDescriptor = new FileReader(archivo);
    } catch (Exception e) {
        throw new ArchivoInexistenteException(archivo);
    }

    if (extensionArchivo.equals("csv")) {
        System.out.println("El archivo de mediciones es de tipo csv");
        return generarMedicionesCSV(fileDescriptor);
    } else if (extensionArchivo.equals("otraExtenion")) {
        return null;
    } else throw new TipoArchivoInvalidoException("There is not implemented a parser for reading " + extensionArchivo + " files");
*/
