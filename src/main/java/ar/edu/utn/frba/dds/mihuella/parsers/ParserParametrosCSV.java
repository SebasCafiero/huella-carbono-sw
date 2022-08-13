package ar.edu.utn.frba.dds.mihuella.parsers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParserParametrosCSV implements ParserParametros {

    @Override
    public Map<String, Float> generarFE(String archivo) throws IOException {
        Map<String, Float> factorEmision = new HashMap<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(archivo));
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String [] data = new String[4];
                int i = 0;
                for (String cell : nextRecord) {
                    data[i]=cell;
                    i++;
                }
                factorEmision.put(data[0] + " -> " + data[1] + " : " + data[2], Float.parseFloat(data[3]));
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("El archivo " + archivo + " no existe");
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return factorEmision;
    }
}
