package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.mediciones.FechaException;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import java.io.IOException;
import java.util.List;

public interface ParserMediciones {
    public List<Medible> generarMediciones(String archivo) throws IOException, FechaException;
}