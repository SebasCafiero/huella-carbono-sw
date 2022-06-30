package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.List;
import java.util.Map;

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

        Map<String,Float> factoresDeEmision = Parser.generarFE(ns.getString("params")); //TODO esto es lo que deciamos que tenia que estar directo en Fachada?
        FachadaOrganizacion calculadora = new FachadaOrganizacion(factoresDeEmision);
        List<Medible> mediciones = Parser.generarMediciones(ns.getString("mediciones"));
        Float huellaCarbono = calculadora.obtenerHU(mediciones);

        System.out.println("La huella de carbono correspondiente a las mediciones ingresadas es: " + huellaCarbono);

        System.out.println("Imprimir datos de las huellas");
    }

}