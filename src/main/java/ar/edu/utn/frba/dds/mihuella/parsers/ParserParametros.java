package ar.edu.utn.frba.dds.mihuella.parsers;

import java.util.Map;

public interface ParserParametros {
    Map<String, Float> generarFE(String archivo) throws Exception;
}
