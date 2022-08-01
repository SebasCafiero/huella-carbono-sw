package ar.edu.utn.frba.dds.mihuella;


import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.mediciones.FechaException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.personas.ContactoMail;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportes;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportesMail;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EnviadorReportes {
    public static void main(String[] args) throws Exception, MedicionSinFactorEmisionException {
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
        parser.addArgument("-pw", "--password").required(true)
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

        Integer anio = 2020;
        Integer mes = 10;

        FachadaOrganizacion fachada = new FachadaOrganizacion();
        fachada.cargarParametros(factoresDeEmision);

        for(String categoria : factoresDeEmision.keySet()) {
            System.out.println(categoria + " -> " + factoresDeEmision.get(categoria));
        }

        AreaSectorial areaReporte = new AreaSectorial("Mataderios");
        organizaciones.get(0).agregarContactoMail("cuentaejemplodds2@gmail.com");
        organizaciones.get(1).agregarContactoMail("rumplestilskink@gmail.com");

        areaReporte.agregarOrganizacion(organizaciones.get(0));
        areaReporte.agregarOrganizacion(organizaciones.get(1));

        AgenteSectorial agente = new AgenteSectorial(areaReporte);
        agente.setContactoMail(new ContactoMail("cuentaejemplodds1", ns.getString("password")));

        NotificadorReportes enviadorReportes = new NotificadorReportesMail()
                .setAgente(agente)
                .setInformacionReporte(agente.crearReporte(anio, mes));

        enviadorReportes.notificarReporte();
    }
}