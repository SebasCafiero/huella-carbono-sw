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


}
