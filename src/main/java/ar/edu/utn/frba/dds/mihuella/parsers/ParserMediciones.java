package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;

import java.io.IOException;
import java.util.List;

public interface ParserMediciones {
    public List<Medicion> generarMediciones(String archivo) throws IOException, FechaException;
}