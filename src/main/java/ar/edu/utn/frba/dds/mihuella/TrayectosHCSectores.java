package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.excepciones.FechaException;
import ar.edu.utn.frba.dds.lugares.*;
//import ar.edu.utn.frba.dds.mediciones.Parser;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.transportes.*;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import com.opencsv.CSVReader;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TrayectosHCSectores {

    private static final String SALIDA_2_PATH = "src/main/resources/salida_TrayectosHC/salida2.csv";

    public static void main(String[] args) throws Exception {
//        ArgumentParser parser = ArgumentParsers.newFor("Checksum").build()
//                .defaultHelp(true)
//                .description("Calculate checksum of given files.");
//        parser.addArgument("-o", "--organizaciones").required(true)
//                .help("Archivo de organizaciones");
//        parser.addArgument("-t", "--trayectos").required(true)
//                .help("Archivo de trayectos");
//        parser.addArgument("-T", "--transportes").required(true)
//                .help("Archivo de transportes");
//        parser.addArgument("-p", "--params").required(true)
//                .help("Archivo con parámetros de configuración");
//        Namespace ns = null;
//        try {
//            ns = parser.parseArgs(args);
//        } catch (ArgumentParserException e) {
//            parser.handleError(e);
//            System.exit(1);
//        }
//
//        System.out.println("Archivo de parametros: " + ns.get("params"));
//        System.out.println("Archivo de organizaciones: " + ns.get("organizaciones"));
//        System.out.println("Archivo de trayectos: " + ns.get("trayectos"));
//        System.out.println("Archivo de transportes: " + ns.get("transportes"));
//
//        String organizacionesJSON = new JSONParser().parse(
//                new FileReader(ns.getString("organizaciones"))).toString();
//
//        String transportesJSON = new JSONParser().parse(
//                new FileReader(ns.getString("transportes"))).toString();
//
////        fachadaTrayecto.mostrarResultadosTransportes();
//        System.out.println("Obtener trayectos");
//
//        FileReader fileDescriptor = new FileReader(ns.getString("trayectos"));
//        CSVReader csvReader = new CSVReader(fileDescriptor);
//        String[] nextRecord = csvReader.readNext();
//
////        while ((nextRecord = csvReader.readNext()) != null) {
////            fachadaTrayecto.cargarTrayecto(nextRecord);
////        }
//
////        fachadaTrayecto.mostrarResultadosOrganizaciones();
////        fachadaTrayecto.mostrarResultadosTransportes();
//
//
//
////        Map<String,Float> factoresDeEmision = Parser.generarFE("src/main/resources/propiedades.csv");
//        FachadaOrganizacion fachada = new FachadaOrganizacion();
////        fachada.cargarParametros(factoresDeEmision);
//
////        Float hcOrg = fachada.obtenerHU(Parser.generarMediciones("src/main/resources/mediciones.csv"));
////        List<Organizacion> organizaciones = fachadaTrayecto.repoOrganizaciones.getOrganizaciones();
//        //TODO HAY QUE ASOCIAR EL REPO TRAYECTOS CON SUS ORGANIZACIONES
//
//        Coordenada coord1 = new Coordenada(10F,10F);
//        Coordenada coord2 = new Coordenada(35F,35F);
//        Coordenada coord3 = new Coordenada(20F,20F);
//        Coordenada coord4 = new Coordenada(2F,2F);
//        Coordenada coordOrg1 = new Coordenada(50F,50F);
//        Coordenada coordOrg2 = new Coordenada(0F,0F);
//
//        Miembro miembro1 = new Miembro("","",TipoDeDocumento.DNI,123,new UbicacionGeografica("",coord1));
//        Miembro miembro2 = new Miembro("","",TipoDeDocumento.DNI,456,new UbicacionGeografica("",coord2));
//        Miembro miembro3 = new Miembro("","",TipoDeDocumento.DNI,789,new UbicacionGeografica("",coord3));
//        Miembro miembro4 = new Miembro("","",TipoDeDocumento.DNI,555,new UbicacionGeografica("",coord4));
//
//        Organizacion org1 = new Organizacion("org1",TipoDeOrganizacionEnum.EMPRESA,new ClasificacionOrganizacion(""),new UbicacionGeografica("",coordOrg1));
//        Organizacion org2 = new Organizacion("org2",TipoDeOrganizacionEnum.EMPRESA,new ClasificacionOrganizacion(""),new UbicacionGeografica("",coordOrg2));
//
//        Sector sector1 = new Sector("sec1",org1);
//        Sector sector2 = new Sector("sec2",org2);
//
//        miembro1.solicitarIngresoAlSector(sector1);
//        miembro2.solicitarIngresoAlSector(sector1);
//        miembro3.solicitarIngresoAlSector(sector2);
//        miembro4.solicitarIngresoAlSector(sector2);
//
//        Trayecto tray1 = new Trayecto(new Tramo(new VehiculoParticular(TipoVehiculo.AUTOMOVIL,TipoCombustible.NAFTA),coord1,coordOrg1));
//        Trayecto tray2 = new Trayecto(new Tramo(new VehiculoParticular(TipoVehiculo.AUTOMOVIL,TipoCombustible.NAFTA),coord2,coordOrg1));
//        Trayecto tray3 = new Trayecto(new Tramo(new VehiculoParticular(TipoVehiculo.AUTOMOVIL,TipoCombustible.NAFTA),coord3,coordOrg2));
//        Trayecto tray4 = new Trayecto(new Tramo(new VehiculoParticular(TipoVehiculo.AUTOMOVIL,TipoCombustible.NAFTA),coord4,coordOrg2));
//
//        miembro1.registrarTrayecto(tray1);
//        miembro2.registrarTrayecto(tray2);
//        miembro3.registrarTrayecto(tray3);
//        miembro4.registrarTrayecto(tray4);
//
////        organizaciones.add(org1);
////        organizaciones.add(org2);

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

        FachadaOrganizacion fachadaOrg = new FachadaOrganizacion();
        fachadaOrg.cargarParametros(factoresDeEmision);

        try { //SALIDA 2
            PrintWriter writer = new PrintWriter(SALIDA_2_PATH, "UTF-8");
            writer.println("Anio, Mes, Razon Social, Sector, Impacto/Cant Miembros");

            Integer anio = 2022;
            Integer mes = 06;

//            organizaciones.stream().forEach(org -> {
//                String razonSocial = org.getRazonSocial();
//                Set<Sector> sectores = org.getSectores();
//                sectores.stream().forEach(sector -> {
//                    String nombreSector = sector.getNombre();
//                    Float impacto = org.obtenerImpactoSector(sector);
//                    writer.println(anio + ", " + mes + ", " + razonSocial + ", " + nombreSector + ", " + impacto);
//                });
//            });
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}