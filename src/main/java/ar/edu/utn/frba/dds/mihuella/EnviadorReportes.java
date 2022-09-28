package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Municipio;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.mapping.TrayectosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaReportes;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.entities.personas.AgenteSectorial;
import ar.edu.utn.frba.dds.entities.personas.ContactoMail;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportesMail;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EnviadorReportes {
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

        FachadaOrganizacion fachadaOrganizacion = new FachadaOrganizacion();
        FachadaTrayectos fachadaTrayectos = new FachadaTrayectos();
        Map<String,Float> factoresDeEmision;
        List<Organizacion> organizaciones;
        List<TramoCSVDTO> trayectosDTO;
        List<MedioDeTransporte> medios;

        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            organizaciones = ParserOrganizacionesJSON.generarOrganizaciones(ns.getString("organizaciones"));
            ParserTransportesJSON.generarTransportes(ns.getString("transportes"));
            trayectosDTO = new ParserTrayectos().capturarEntradas(ns.getString("trayectos"));
        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        fachadaOrganizacion.cargarParametros(factoresDeEmision);
        trayectosDTO.forEach(tr -> {
            boolean esCompartidoPasivo = tr.getTrayectoId().equals("0");
            if(!esCompartidoPasivo) {
                fachadaTrayectos.cargarTrayectoActivo(TrayectosMapper.toNuevoTrayectoDTO(tr));
            } else {
                fachadaTrayectos.cargarTrayectoPasivo(TrayectosMapper.toTrayectoCompartidoDTO(tr));
            }
        });

        Integer anio = 2020;
        Integer mes = 10;

        for(String categoria : factoresDeEmision.keySet()) {
            System.out.println(categoria + " -> " + factoresDeEmision.get(categoria));
        }

        AreaSectorial areaReporte = new Municipio("Ciudad de Buenos Aires","Ciudad de Buenos Aires", "Argentina");
        organizaciones.get(0).agregarContactoMail("cuentaejemplodds2@gmail.com");
        organizaciones.get(1).agregarContactoMail("rumplestilskink@gmail.com");

        areaReporte.agregarOrganizacion(organizaciones.get(0));
        areaReporte.agregarOrganizacion(organizaciones.get(1));

        AgenteSectorial agente = new AgenteSectorial(areaReporte);
        areaReporte.setAgente(agente);
//        agente.setContactoMail(new ContactoMail("cuentaejemplodds2", ""));
        agente.setContactoMail(new ContactoMail("cuentaejemplodds1", ns.getString("password")));
        FachadaReportes fachadaReportes = new FachadaReportes();
        fachadaReportes
                .setNotificador(new NotificadorReportesMail())
                .generarReporteAgente(areaReporte, anio, mes);
    }
}