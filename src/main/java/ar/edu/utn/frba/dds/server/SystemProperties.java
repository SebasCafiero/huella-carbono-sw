package ar.edu.utn.frba.dds.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class SystemProperties {
    private static final Boolean jpa;
    private static final Float delta;
    private static final Float coeficienteGradoKm;
    private static final Boolean calculadoraDistanciasMockEnabled;
    private static final Boolean calculadoraDistanciasCacheEnabled;
    private static final String calculadoraDistanciasUrl;
    private static final String calculadoraDistanciasToken;

    static {
        File archivo = null;
        try {
            Properties propiedades = new Properties();
            archivo = new File("resources/aplication.properties");
            FileReader file = new FileReader("resources/aplication.properties");
            propiedades.load(file);

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
            calculadoraDistanciasToken = propiedades.getProperty("client.calculadora.distancias.api.token");

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println(archivo.getAbsolutePath());
            System.out.println(e.getMessage());
            throw new RuntimeException("El archivo properties no existe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

}
