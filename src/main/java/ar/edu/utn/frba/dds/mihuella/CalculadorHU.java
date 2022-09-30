package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.mediciones.BatchMedicion;
import ar.edu.utn.frba.dds.entities.mediciones.FactorEmision;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.mapping.MedicionMapper;
import ar.edu.utn.frba.dds.mihuella.dto.MedicionCSVDTO;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserMedicionesCSV;
import ar.edu.utn.frba.dds.mihuella.parsers.ParserParametrosCSV;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.repositories.RepoFactores;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.impl.memory.RepoFactoresMemoria;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculadorHU {

    public static void main(String[] args) {

        ArgumentParser parser = ArgumentParsers.newFor("Checksum").build()
                .defaultHelp(true)
                .description("Calculate checksum of given files.");
        parser.addArgument("-m", "--mediciones").required(true)
                .help("Archivo de mediciones");
        parser.addArgument("-p", "--params").required(true)
                .help("Archivo con parámetros de configuración");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        System.out.println("Archivo de mediciones: " + ns.get("mediciones"));
        System.out.println("Archivo de parametros: " + ns.get("params"));

        Map<String,Float> factoresDeEmision;
        List<MedicionCSVDTO> medicionesDTO;
        try {
            factoresDeEmision = new ParserParametrosCSV().generarFE(ns.getString("params"));
            medicionesDTO = new ParserMedicionesCSV().generarMediciones(ns.getString("mediciones"));
        } catch (IOException | FechaException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        Repositorio<Medicion> repoMediciones = FactoryRepositorio.get(Medicion.class);
        List<Medicion> mediciones = medicionesDTO.stream().map(MedicionMapper::toEntity).collect(Collectors.toList());
        mediciones.forEach(repoMediciones::agregar);

        FachadaOrganizacion calculadora = new FachadaOrganizacion();
        calculadora.cargarParametros(factoresDeEmision);

        Repositorio<BatchMedicion> repoBatch = FactoryRepositorio.get(BatchMedicion.class);
        repoBatch.agregar(new BatchMedicion(mediciones, LocalDate.now()));

        Float hcOrg;
        List<Medible> medibles = new ArrayList<>(mediciones);
        try {
            hcOrg = calculadora.obtenerHU(medibles);
        } catch(MedicionSinFactorEmisionException ex) {
            System.out.println("No hay factor de emision definido para la categoria '" + ex.getCategoria() + "'");
            return;
        }

        System.out.println("La huella de carbono correspondiente a las mediciones ingresadas es: " + hcOrg);
        System.exit(0);
    }
}