package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.excepciones.FechaException;
import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class TrayectosHCMiembros {

    private static final String SALIDA_1_PATH = "resources/salida1.csv";

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
            System.out.println(medios.toString());
            trayectos = new ParserTrayectos().generarTrayectos(ns.getString("trayectos"), organizaciones, medios);
        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("Obtener trayectos");

        FachadaOrganizacion fachada = new FachadaOrganizacion();
        fachada.cargarParametros(factoresDeEmision);

        System.out.println("Cargue parametros");

        try { //SALIDA 1
            PrintWriter writer = new PrintWriter(SALIDA_1_PATH, "UTF-8");
            writer.println("Anio, Mes, Razon Social, DNI, Impacto");
            System.out.println("Anio, Mes, Razon Social, DNI, Impacto");

            Integer anio = 2022;
            int mes = 06; //TODO

            for(Organizacion org : organizaciones) {
                System.out.println("Entro a organizaciones");
                String razonSocial = org.getRazonSocial();
                Set<Miembro> miembros = org.miembros();
                Float consumoTotalOrganizacion = fachada.obtenerHU(miembros.stream()
                        .flatMap(miembro -> miembro.getTrayectos().stream())
                        .flatMap(trayecto -> trayecto.getTramos().stream())
                        .collect(Collectors.toList()));

                System.out.println("Consumo total de la organizacion: " + consumoTotalOrganizacion);
                for (Miembro miembro : miembros) {
                    Integer documento = miembro.getNroDocumento();
                    List<Medible> mediblesMiembro = miembro.getTrayectos().stream()
                            .flatMap(trayecto -> trayecto.getTramos().stream())
                            .collect(Collectors.toList());
                    float impactoAbsoluto = fachada.obtenerHU(mediblesMiembro);
                    float impacto = 100 * impactoAbsoluto / consumoTotalOrganizacion;
                    System.out.println("Consumo total miembro de organizacion: " + impactoAbsoluto);
                    writer.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
                    System.out.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
                }
            }
            writer.close();
        } catch (Exception | MedicionSinFactorEmisionException e) {
            System.out.println(e.getMessage());
        }

    }
}