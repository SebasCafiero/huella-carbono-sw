package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mapping.TrayectosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class TrayectosHCMiembros {

    private static final String SALIDA_1_PATH = "resources/salida1.csv"/*"src/main/resources/salida1.csv"*/;

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

        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            organizaciones = ParserOrganizacionesJSON.generarOrganizaciones(ns.getString("organizaciones"));
            ParserTransportesJSON.generarTransportes(ns.getString("transportes"));
            ParserTrayectos.generarTrayectos(ns.getString("trayectos"));
            //TODO LOS RETURNS ^^ NO SON USADOS XQ SE CARGAN EN LOS REPO
        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        FachadaOrganizacion fachada = new FachadaOrganizacion();
        fachada.cargarParametros(factoresDeEmision);

        //SALIDA 1
        PrintWriter writer = new PrintWriter(SALIDA_1_PATH, "UTF-8");
        writer.println("Anio, Mes, Razon Social, DNI, Impacto");

        Integer anio = 2020;
        int mes = 10; //TODO

        for(Organizacion org : organizaciones) {
            String razonSocial = org.getRazonSocial();
            Set<Miembro> miembros = org.getMiembros();

            Float consumoTotalOrganizacion = fachada.getImpactoTrayectosOrganizacion(org, anio, mes);
//                System.out.println("Consumo total de la organizacion: " + consumoTotalOrganizacion);
            for (Miembro miembro : miembros) {
                Integer documento = miembro.getNroDocumento();
                System.out.println("MiembroID: " + documento);
                float impacto = 100 * fachada.getTrayectosDeMiembro(miembro, anio, mes) / consumoTotalOrganizacion;
                writer.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
                System.out.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto + '\n');
            }
        }
        writer.close();

    }
}