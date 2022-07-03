package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.excepciones.FechaException;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ParserMediciones {
    public List<Medible> generarMediciones(String archivo) throws IOException, FechaException;
}