package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.mediciones.FechaException;
import ar.edu.utn.frba.dds.lugares.*;
//import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.transportes.*;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrayectosHCSectores {

    private static final String SALIDA_2_PATH = "resources/salida2.csv";

    public static void main(String[] args) throws Exception {
        ArgumentParser parser = ArgumentParsers.newFor("Checksum").build()
                .defaultHelp(true)
                .description("Calculate checksum of given files.");
        parser.addArgument("-o", "--organizaciones").required(true)
                .help("Archivo de organizaciones");
        parser.addArgument("-t", "--trayectos").required(true)
                .help("Archivo de trayectos");
        parser.addArgument("-T", "--transportes").required(true)
                .help("Archivo de transportes");
        parser.addArgument("-p", "--params").required(true)
                .help("Archivo con parámetros de configuración");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        System.out.println("Archivo de parametros: " + ns.get("params"));
        System.out.println("Archivo de organizaciones: " + ns.get("organizaciones"));
        System.out.println("Archivo de trayectos: " + ns.get("trayectos"));
        System.out.println("Archivo de transportes: " + ns.get("transportes"));

        Map<String,Float> factoresDeEmision;
        List<Organizacion> organizaciones;
        List<Trayecto> trayectos;
        List<MedioDeTransporte> medios;

        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            organizaciones = new ParserOrganizaciones().cargarOrganizaciones(ns.getString("organizaciones"));
            medios = new ParserTransportes().cargarTransportes(ns.getString("transportes"));
            trayectos = new ParserTrayectos().generarTrayectos(ns.getString("trayectos"), organizaciones, medios);
        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("Obtener trayectos");

        FachadaOrganizacion fachada = new FachadaOrganizacion();
        fachada.cargarParametros(factoresDeEmision);

        try { //SALIDA 2
            PrintWriter writer = new PrintWriter(SALIDA_2_PATH, "UTF-8");
            writer.println("Anio, Mes, Razon Social, Sector, Impacto/Cant Miembros");

            Integer anio = 2022;
            Integer mes = 06; //TODO

            for(Organizacion org : organizaciones){
                String razonSocial = org.getRazonSocial();
                Set<Sector> sectores = org.getSectores();

                for(Sector sector : sectores){
                    String nombreSector = sector.getNombre();
                    Float impacto = fachada.obtenerImpactoSector(org,sector);
                    writer.println(anio + ", " + mes + ", " + razonSocial + ", " + nombreSector + ", " + impacto);
                }
            }
            writer.close();
        } catch (Exception | MedicionSinFactorEmisionException e) {
            e.printStackTrace();
        }
    }

}