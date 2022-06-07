package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.transportes.TransportePublico;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import com.opencsv.CSVReader;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrayectosHC {

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

        String organizacionesJSON = new JSONParser().parse(
                new FileReader(ns.getString("organizaciones"))).toString();

        String transportesJSON = new JSONParser().parse(
                new FileReader(ns.getString("transportes"))).toString();

        FachadaTrayecto fachadaTrayecto = new FachadaTrayecto();
        fachadaTrayecto.cargarOrganizaciones(organizacionesJSON);
        fachadaTrayecto.cargarTransportes(transportesJSON);

//        fachadaTrayecto.mostrarResultadosTransportes();
        System.out.println("Obtener trayectos");

        FileReader fileDescriptor = new FileReader(ns.getString("trayectos"));
        CSVReader csvReader = new CSVReader(fileDescriptor);
        String[] nextRecord = csvReader.readNext();

        while ((nextRecord = csvReader.readNext()) != null) {
            fachadaTrayecto.cargarTrayecto(nextRecord);
        }

        fachadaTrayecto.mostrarResultadosOrganizaciones();
        fachadaTrayecto.mostrarResultadosTransportes();

        try {
            PrintWriter writer = new PrintWriter("resources/salida_TrayectosHC/salida1.csv", "UTF-8");
            writer.println("Anio, Mes, Razon Social, DNI, Impacto");
            Integer anio = 2012;
            Integer mes = 05;
            String razonSocial = "UTN";
            Integer documento = 12345678;
            Double impacto = 12.43;
            writer.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ClasificacionOrganizacion clasificacionDesdeNombre(String categoria) {
        return new ClasificacionOrganizacion(categoria);
    }

}