package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mediciones.CalculadoraHCOrganizacion;
import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.List;

public class TrayectosHC {

    public static int main(String[] args) throws Exception {
        ArgumentParser parser = ArgumentParsers.newFor("Checksum").build()
                .defaultHelp(true)
                .description("Calculate checksum of given files.");
        parser.addArgument("-o", "--organizaciones").required(true)
                .help("Archivo de organizaciones");
        parser.addArgument("-t", "--trayectos").required(true)
                .help("Archivo de trayectos");
        parser.addArgument("-T", "--transportes").required(true)
                .help("Archivo de transportes");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        System.out.println("Archivo de organizaciones: " + ns.get("organizaciones"));
        System.out.println("Archivo de trayectos: " + ns.get("trayectos"));
        System.out.println("Archivo de transportes: " + ns.get("transportes"));

        // calcular huella de las actividades y el total
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion(ns.getString("params"));
        List<Medible> mediciones = Parser.generarMediciones(ns.getString("mediciones"));


        /*
        creamos los archivos y los asignamos al directoria salida_TrayectosHC
        arch 1:
        Año y mes [AAAAMes],Razón Social Org, DNI Miembro, Impacto (% HC de trayectos , con 2 decimales de precisión
        use punto “.” como separador decimal, los de una org/periodo tienen que sumar 100)

        arch 2:
        Año y mes [AAAAMes],Razón Social Org,Nombre Sector,HC / Cant (redondee decimales)
         */

        System.out.println("Imprimir datos de las huellas");
        return 0;
    }
}