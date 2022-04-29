package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.FileReader;
import java.util.Collection;

public class CalculadorHU {

    public static void main(String[] args) {


        ArgumentParser parser = ArgumentParsers.newFor("Checksum").build()
                .defaultHelp(true)
                .description("Calculate checksum of given files.");
        parser.addArgument("-m", "--mediciones").required(true)
                .help("Archivo de mediciones");
        parser.addArgument("-p", "--params").required(true)
                .help("Archivo con parámetros de configuración");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        System.out.println("Archivo de mediciones: " + ns.get("mediciones"));
        System.out.println("Archivo de parametros: " + ns.get("params"));
        // calcular huella de las actividades y el total
        /*
        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader((String) ns.get("mediciones"));

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
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

         */
        String extensionMediciones = "";
        int i = ns.getString("mediciones").lastIndexOf('.');
        if (i > 0) {
            extensionMediciones = ns.getString("mediciones").substring(i + 1);
        } //else throws Exception;

        if(extensionMediciones.equals("csv"))
            // Parser parserMdiciones = new ParserCSV();
            System.out.println("El archivo de mediciones es de tipo csv");
        else {
            System.out.println("There is not implemented a parser for reading " + extensionMediciones + " files");
            return;
        }
        /*
        Collection<Medible> mediciones = parserMediciones.mapearArchivo(ns.getString("mediciones"));
        Collection<FactorEmision> factoresDeEmision = ParserJSON.mapearArchivo(ns.getString("params"));

        CalculadorHU calculadora = new CalculadorHU();
        calculadora.factoresEmision = factoresDeEmision;
        Float huellaCarbono = calculadora.obtenerHC(mediciones);

         */

        System.out.println("Imprimir datos de las huellas");
    }

}