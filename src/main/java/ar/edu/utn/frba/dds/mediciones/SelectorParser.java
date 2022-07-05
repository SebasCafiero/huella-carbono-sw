package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectorParser {

//    public static List<Medible> generarMediciones(String archivo) throws Exception, ArchivoSinExtensionException {
//        String extensionArchivo = "";
//        int index = archivo.lastIndexOf('.');
//        if (index > 0)
//            extensionArchivo = archivo.substring(index + 1);
//        else throw new ArchivoSinExtensionException(archivo);

//        FileReader fileDescriptor;
//        try {
//            fileDescriptor = new FileReader(archivo);
//        } catch (Exception e) {
//            throw new ArchivoInexistenteException(archivo);
//        }

//        if (extensionArchivo.equals("csv")) {
//            System.out.println("El archivo de mediciones es de tipo csv");
//            return generarMedicionesCSV(fileDescriptor);
//        } else if (extensionArchivo.equals("otraExtenion")) {
//            return null;
//        } else throw new TipoArchivoInvalidoException("There is not implemented a parser for reading " + extensionArchivo + " files");
//    }

    public static Map<String, Float> generarFE(String archivo) throws Exception {

        FileReader fileDescriptor;
        Map<String, Float> factorEmision = new HashMap<>();
        try {
            fileDescriptor = new FileReader(archivo);
            CSVReader csvReader = new CSVReader(fileDescriptor);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String [] data = new String[4];
                int i = 0;
                for (String cell : nextRecord) {
                    data[i]=cell;
                    i++;
                }
                factorEmision.put(data[0]+" - "+data[1], Float.parseFloat(data[3]));
            }

        }
        catch (Exception e) {
            throw new Exception("El archivo no existe");
        }

        return factorEmision;
    }
}