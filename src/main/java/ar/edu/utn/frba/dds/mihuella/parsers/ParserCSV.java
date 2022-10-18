package ar.edu.utn.frba.dds.mihuella.parsers;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParserCSV<T> {
    private final Class<T> type;

    public ParserCSV(Class<T> type) {
        this.type = type;
    }

    public List<T> parseFileToCollection(String archivo) throws IOException {
        List<T> list = new CsvToBeanBuilder(new FileReader(archivo))
                .withType(type)
                .build()
                .parse();

        return list;
    }
}
