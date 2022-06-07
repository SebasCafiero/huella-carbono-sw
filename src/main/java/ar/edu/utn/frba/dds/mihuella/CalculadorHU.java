package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mediciones.CalculadoraHCOrganizacion;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.FileReader;
import java.util.Collection;
import java.util.List;

public class CalculadorHU {

    public static void main(String[] args) throws Exception {


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
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion(ns.getString("params"));
        List<Medible> mediciones = Parser.generarMediciones(ns.getString("mediciones"));

        System.out.println("La huella de carbono correspondiente a las mediciones ingresadas es: " + calculadora.obtenerHU(mediciones));

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