package ar.edu.utn.frba.dds.mediciones;

import ar.edu.utn.frba.dds.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
    public static List<Organizacion> generarOrganizaciones(String archivo, Namespace ns) throws Exception {

        FileReader fileDescriptor;
        List<Organizacion> organizaciones = new ArrayList<>();
        try {
            fileDescriptor = new FileReader(archivo);
            CSVReader csvReader = new CSVReader(fileDescriptor);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String [] data = new String[7];
                int i = 0;
                for (String cell : nextRecord) {
                    data[i]=cell;
                    i++;
                }
                Organizacion organizacion = new Organizacion(data[0], TipoDeOrganizacionEnum.valueOf(data[1]), new ClasificacionOrganizacion(data[2]), new UbicacionGeografica(data[3], Float.parseFloat(data[4]), Float.parseFloat(data[5])));
                List<Medible> mediciones = Parser.generarMediciones(ns.getString(data[6]));
                organizacion.agregarMediciones(mediciones);
                organizaciones.add(organizacion);
            }

        }
        catch (Exception e) {
            throw new Exception("El archivo no existe");
        }

        return organizaciones;
    }

    private static List<Medible> generarMedicionesCSV(FileReader archivo) {
        List<Medible> mediciones = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(archivo);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                String [] data = new String[6];
                int i = 0;
                for (String cell : nextRecord) {
                    data[i]=cell;
                    i++;
                }
                mediciones.add(new Medicion(data[0]+" - "+data[1], data[2], Float.parseFloat(data[3]), data[4],data[5]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return mediciones;
    }
}