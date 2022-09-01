package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.mapping.TrayectosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
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
        List<TramoCSVDTO> tramosCSV;
        List<MedioDeTransporte> medios;

        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            organizaciones = new ParserOrganizaciones().cargarOrganizaciones(ns.getString("organizaciones"));
//            organizaciones = ParserOrganizacionesJSON.generarOrganizaciones(ns.getString("organizaciones"));
            medios = new ParserTransportes().cargarTransportes(ns.getString("transportes"));
//            medios = ParserTransportesJSON.generarTransportes(ns.getString("transportes"));
            System.out.println(medios.toString());
//            ParserTrayectos.generarTrayectos2(ns.getString("trayectos"));
            tramosCSV = new ParserTrayectos().capturarEntradas(ns.getString("trayectos"));
            tramosCSV.forEach(tr -> {
                boolean esCompartidoPasivo = tr.getTrayectoId().equals("0");
                if(!esCompartidoPasivo) {
                    new ParserTrayectos().cargarTrayectoActivo(TrayectosMapper.toNuevoTrayectoDTO(tr), tr.getPeriodicidad().trim().charAt(0), tr.getFecha().trim());
                } else {
                    new ParserTrayectos().cargarTrayectoPasivo(TrayectosMapper.toTrayectoCompartidoDTO(tr));
                }
            });
        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("Obtener trayectos");

        FachadaOrganizacion fachada = new FachadaOrganizacion();
        fachada.cargarParametros(factoresDeEmision);

        System.out.println("Cargue parametros");

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
                System.out.println("\nMiembroID: " + documento);
                float impacto = 100 * fachada.getTrayectosDeMiembro(miembro, anio, mes) / consumoTotalOrganizacion;
                writer.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
                System.out.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
            }
        }
        writer.close();

    }
}

/*
Con el parser csv sin pojo
Anio, Mes, Razon Social, DNI, Impacto
2020, 10, UTN, 2, 60.638298
2020, 10, UTN, 1, 26.910055
2020, 10, UTN, 3, 12.451645
2020, 10, UTN, 4, 0.0
2020, 10, Coca Cola, 5, NaN
2020, 10, Coca Cola, 6, NaN
2020, 10, Coca Cola, 7, NaN
2020, 10, Coca Cola, 8, NaN


Con el parser CSV con pojo, sin compartidos
Anio, Mes, Razon Social, DNI, Impacto
2020, 10, UTN, 2, 53.985302
2020, 10, UTN, 1, 31.458445
2020, 10, UTN, 3, 14.556247
2020, 10, UTN, 4, 0.0
2020, 10, Coca Cola, 5, NaN
2020, 10, Coca Cola, 6, NaN
2020, 10, Coca Cola, 7, NaN
2020, 10, Coca Cola, 8, NaN

Con el parser CSV con pojo y compartidos (COINCIDE CON LO CALCULADO MANUAL)
Anio, Mes, Razon Social, DNI, Impacto
2020, 10, UTN, 2, 62.436405
2020, 10, UTN, 1, 23.007345
2020, 10, UTN, 3, 14.556247
2020, 10, UTN, 4, 0.0
2020, 10, Coca Cola, 5, NaN
2020, 10, Coca Cola, 6, NaN
2020, 10, Coca Cola, 7, NaN
2020, 10, Coca Cola, 8, NaN

* */