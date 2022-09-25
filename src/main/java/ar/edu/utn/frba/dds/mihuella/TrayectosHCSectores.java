package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.Sector;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.mapping.TrayectosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
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
        List<TramoCSVDTO> trayectosDTO;
        FachadaOrganizacion fachada = new FachadaOrganizacion();
        FachadaTrayectos fachadaTrayectos = new FachadaTrayectos();

        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            organizaciones = ParserOrganizacionesJSON.generarOrganizaciones(ns.getString("organizaciones"));
            ParserTransportesJSON.generarTransportes(ns.getString("transportes"));
            trayectosDTO = new ParserTrayectos().capturarEntradas(ns.getString("trayectos"));
        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        fachada.cargarParametros(factoresDeEmision);
        trayectosDTO.forEach(tr -> {
            boolean esCompartidoPasivo = tr.getTrayectoId().equals("0");
            if(!esCompartidoPasivo) {
                fachadaTrayectos.cargarTrayectoActivo(TrayectosMapper.toNuevoTrayectoDTO(tr));
            } else {
                fachadaTrayectos.cargarTrayectoPasivo(TrayectosMapper.toTrayectoCompartidoDTO(tr));
            }
        });

        //SALIDA 2
        PrintWriter writer = new PrintWriter(SALIDA_2_PATH, "UTF-8");
        writer.println("Anio, Mes, Razon Social, Sector, Impacto/Cant Miembros");

        Integer anio = 2020;
        Integer mes = 10;

        for(Organizacion org : organizaciones){
            String razonSocial = org.getRazonSocial();
            Set<Sector> sectores = org.getSectores();
            Float consumoTotalOrganizacion = fachada.getImpactoTrayectosOrganizacion(org, anio, mes);

            for(Sector sector : sectores){
                String nombreSector = sector.getNombre();
                System.out.println("Sector: " + nombreSector);
                float impacto = fachada.getImpactoSector(sector, anio, mes) / consumoTotalOrganizacion;
                writer.println(anio + ", " + mes + ", " + razonSocial + ", " + nombreSector + ", " + impacto);
                System.out.println(anio + ", " + mes + ", " + razonSocial + ", " + nombreSector + ", " + impacto + '\n');
            }
        }
        writer.close();
    }

}
