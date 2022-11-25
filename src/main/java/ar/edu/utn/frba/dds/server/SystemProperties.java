package ar.edu.utn.frba.dds.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SystemProperties {
    private static final Boolean jpa;
    private static final Float delta;
    private static final Float coeficienteGradoKm;
    private static final Boolean calculadoraDistanciasMockEnabled;
    private static final Boolean calculadoraDistanciasCacheEnabled;
    private static final String calculadoraDistanciasUrl;
    private static final String calculadoraDistanciasToken;

    static {
        Map<String, String> varEntorno = System.getenv();
        Properties propArchivo = cargarArchivoConfigurable();
        System.out.println("aplication (entorno): " + varEntorno);
        jpa = varEntorno.getOrDefault("jpa", propArchivo.getProperty("jpa")).equals("true");
        delta = Float.parseFloat(
                varEntorno.getOrDefault("coordenadas.precision.delta",
                        propArchivo.getProperty("coordenadas.precision.delta", "0.00001")));
        coeficienteGradoKm = Float.parseFloat(
                varEntorno.getOrDefault("coordenadas.precision.equivalencia",
                        propArchivo.getProperty("coordenadas.precision.equivalencia", "111.10")));
        calculadoraDistanciasMockEnabled = varEntorno.getOrDefault("client.calculadora.distancias.mock-enabled",
                propArchivo.getProperty("client.calculadora.distancias.mock-enabled")).equals("true");
        calculadoraDistanciasCacheEnabled = varEntorno.getOrDefault("client.calculadora.distancias.cache-enabled",
                propArchivo.getProperty("client.calculadora.distancias.cache-enabled")).equals("true");
        calculadoraDistanciasUrl = varEntorno.getOrDefault("client.calculadora.distancias.api.url",
                propArchivo.getProperty("client.calculadora.distancias.api.url", "https://ddstpa.com.ar/api/"));
        calculadoraDistanciasToken = varEntorno.getOrDefault("client.calculadora.distancias.api.token",
                propArchivo.getProperty("client.calculadora.distancias.api.token", ""));
    }

    public static Boolean isJpa() {
        return jpa;
    }

    public static Float getDelta() {
        return delta;
    }

    public static Float getCoeficienteGradoKm() {
        return coeficienteGradoKm;
    }

    public static Boolean isCalculadoraDistanciasMockEnabled() {
        return calculadoraDistanciasMockEnabled;
    }

    public static Boolean isCalculadoraDistanciasCacheEnabled() {
        return calculadoraDistanciasCacheEnabled;
    }

    public static String getCalculadoraDistanciasUrl() {
        return calculadoraDistanciasUrl;
    }

    public static String getCalculadoraDistanciasToken() {
        return calculadoraDistanciasToken;
    }

    private static Properties cargarArchivoConfigurable() {
        Properties propiedades = new Properties();
        String path = "resources/aplication.properties";
//        File archivo = null;
        try {
//            archivo = new File(path);
            FileReader file = new FileReader(path);
            propiedades.load(file);

            System.out.println("aplication.properties: "+propiedades);

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo " + path);
//            System.out.println(archivo.getAbsolutePath());
//            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Excepcion!");
            throw new RuntimeException(e);
        }
        return propiedades;
    }
}
/*
  jpa = Objects.equals(propiedades.getProperty("jpa"), "true");
          delta = Float.parseFloat(
          propiedades.getProperty("coordenadas.precision.delta", "0.00001"));
          coeficienteGradoKm = Float.parseFloat(
          propiedades.getProperty("coordenadas.precision.equivalencia", "111.10"));
          calculadoraDistanciasMockEnabled = Objects.equals(
          propiedades.getProperty("client.calculadora.distancias.mock-enabled"), "true");
          calculadoraDistanciasCacheEnabled = Objects.equals(
          propiedades.getProperty("client.calculadora.distancias.cache-enabled"), "true");
          calculadoraDistanciasUrl = propiedades.getProperty("client.calculadora.distancias.api.url");
          calculadoraDistanciasToken = propiedades.getProperty("client.calculadora.distancias.api.token");*/
