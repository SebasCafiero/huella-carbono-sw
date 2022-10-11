package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mapping.FactorEmisionMapper;
import ar.edu.utn.frba.dds.mapping.OrganizacionMapper;
import ar.edu.utn.frba.dds.mapping.TransportesMapper;
import ar.edu.utn.frba.dds.mapping.TrayectosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.FactorEmisionCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.OrganizacionConMiembrosJSONDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TransporteJSONDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaTrayectos;
import ar.edu.utn.frba.dds.mihuella.fachada.NoExisteMedioException;
import ar.edu.utn.frba.dds.mihuella.parsers.*;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

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
        FachadaOrganizacion fachada = new FachadaOrganizacion();

        FactoryRepositorio.get(Trayecto.class).buscarTodos().forEach(System.out::println);
        FactoryRepositorio.get(Tramo.class).buscarTodos().forEach(System.out::println);

        try {
            factoresDeEmision = cargarFE(ns.getString("params"));
            organizaciones = cargarOrganizaciones(ns.getString("organizaciones"));
            cargarTransportes(ns.getString("transportes"));
            cargarTrayectos(ns.getString("trayectos"));

        } catch (IOException | FechaException | NoExisteMedioException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        fachada.cargarParametros(factoresDeEmision);

        //SALIDA 1
        PrintWriter writer = new PrintWriter(SALIDA_1_PATH, "UTF-8");
        writer.println("Anio, Mes, Razon Social, DNI, Impacto");

        Integer anio = 2020; //TODO
        int mes = 10;


        fachada.mostrarParametros();

        for(Organizacion org : organizaciones) {
            String razonSocial = org.getRazonSocial();
            Set<Miembro> miembros = org.getMiembros();

            Float consumoTotalOrganizacion = fachada.calcularImpactoOrganizacion(org, new Periodo(anio, mes));
//                System.out.println("Consumo total de la organizacion: " + consumoTotalOrganizacion);
            for (Miembro miembro : miembros) {
                Integer documento = miembro.getNroDocumento();
                System.out.println("MiembroID: " + documento);
                float impacto = 100 * fachada.calcularImpactoTrayectos(miembro, new Periodo(anio, mes)) / consumoTotalOrganizacion;
                writer.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto);
                System.out.println(anio + ", " + mes + ", " + razonSocial + ", " + documento + ", " + impacto + '\n');
            }
        }
        writer.close();
        System.exit(0);
    }

    public static Map<String,Float> cargarFE(String archFE) throws IOException {
        List<FactorEmisionCSVDTO> factoresDeEmision = new ParserCSV<>(FactorEmisionCSVDTO.class)
                .parseFileToCollection(archFE);

        Map<String,Float> mapaFactores = factoresDeEmision.stream()
                .map(FactorEmisionMapper::toEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return mapaFactores;
    }

    public static List<Organizacion> cargarOrganizaciones(String archOrg) throws IOException {
        Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);

        List<OrganizacionConMiembrosJSONDTO> organizacionesDTO = new ParserJSON<>(OrganizacionConMiembrosJSONDTO.class).parseFileToCollection(archOrg);

        return organizacionesDTO.stream().map(organizacionDTO -> {
            Organizacion unaOrg = OrganizacionMapper.toEntity(organizacionDTO);
            unaOrg.getMiembros().forEach(repoMiembros::agregar);
            return unaOrg;
        }).collect(Collectors.toList());
    }

    public static void cargarTransportes(String archTrans) throws IOException {
        List<TransporteJSONDTO> transportesDTO = new ParserJSON<>(TransporteJSONDTO.class).parseFileToCollection(archTrans);
        Repositorio<MedioDeTransporte> repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);

        for(TransporteJSONDTO transporteDTO : transportesDTO){
            MedioDeTransporte unTransporte = TransportesMapper.toEntity(transporteDTO);
            repoMedios.agregar(unTransporte);
        }
    }

    public static void cargarTrayectos(String archTray) throws IOException {
        FachadaTrayectos fachadaTrayectos = new FachadaTrayectos();

        List<TramoCSVDTO> trayectosDTO = new ParserCSV<>(TramoCSVDTO.class).parseFileToCollection(archTray);

        trayectosDTO.forEach(tr -> {
            boolean esCompartidoPasivo = tr.getTrayectoId().equals("0");
            if(!esCompartidoPasivo) {
                fachadaTrayectos.cargarTrayectoActivo(TrayectosMapper.toNuevoTrayectoDTO(tr));
            } else {
                fachadaTrayectos.cargarTrayectoPasivo(TrayectosMapper.toTrayectoCompartidoDTO(tr));
            }
        });

        fachadaTrayectos.mostrarTrayectos();
    }
}