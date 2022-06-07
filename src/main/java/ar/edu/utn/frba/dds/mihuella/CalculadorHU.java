package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.lugares.ClasificacionOrganizacion;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.TipoDeOrganizacionEnum;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
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
        parser.addArgument("-m1", "--mediciones1").required(true)
                .help("Archivo de mediciones");
        parser.addArgument("-m2", "--mediciones2").required(true)
                .help("Archivo de mediciones");
        parser.addArgument("-p", "--params").required(true)
                .help("Archivo con parámetros de configuración");
        parser.addArgument("-o", "--organizaciones").required(true)
                .help("Archivo de mediciones");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        // calcular huella de las actividades y el total
        List<Organizacion> organizaciones = Parser.generarOrganizaciones(ns.get("organizaciones"), ns);

        for( Organizacion organizacion : organizaciones){
            System.out.println("La huella de carbono correspondiente a las mediciones ingresadas es: " + organizacion.obtenerHC(ns.getString("params")));
        }

        System.out.println("Imprimir datos de las huellas");
    }

}