package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class Parser {
    public static List<Medible> generarMediciones(String archivo) throws Exception {
        String extensionArchivo = "";
        int index = archivo.lastIndexOf('.');
        if (index > 0)
            extensionArchivo = archivo.substring(index + 1);
        else throw new Exception("Nombre de archivo inválido");

        FileReader fileDescriptor;
        try {
            fileDescriptor = new FileReader(archivo);
        } catch (Exception e) {
            throw new Exception("El archivo no existe");
        }

        if (extensionArchivo.equals("csv")) {
            System.out.println("El archivo de mediciones es de tipo csv");
            return generarMedicionesCSV(fileDescriptor);
        } else if (extensionArchivo.equals("otraExtenion")) {
            return null;
        } else throw new Exception("There is not implemented a parser for reading " + extensionArchivo + " files");
    }

    public static Map<String, Float> generarFE(String archivo) {
        return null;
    }

    private static List<Medible> generarMedicionesCSV(FileReader archivo) {
        try {
            CSVReader csvReader = new CSVReader(archivo);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // TODO
        return null;
    }
}