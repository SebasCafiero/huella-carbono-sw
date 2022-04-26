package ar.edu.utn.frba.dds.mihuella;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


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
        System.out.println("Imprimir datos de las huellas");
    }

}
