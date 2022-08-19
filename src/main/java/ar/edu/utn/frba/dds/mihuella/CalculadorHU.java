package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserMedicionesCSV;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserParametrosCSV;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        Map<String,Float> factoresDeEmision;
        List<Medible> mediciones;
        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            mediciones = new ArrayList<>(new ParserMedicionesCSV().generarMediciones(ns.getString("mediciones")));
        } catch (IOException | FechaException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        FachadaOrganizacion calculadora = new FachadaOrganizacion();
        calculadora.cargarParametros(factoresDeEmision);

        Float hcOrg;
        try {
            hcOrg = calculadora.obtenerHU(mediciones);
        } catch(MedicionSinFactorEmisionException ex) {
            System.out.println("No hay factor de emision definido para la categoria '" + ex.getCategoria() + "'");
            return;
        }

        System.out.println("La huella de carbono correspondiente a las mediciones ingresadas es: " + hcOrg);
    }

}