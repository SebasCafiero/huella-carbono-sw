package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.trayectos.servicios.AdaptadorServicioDDSTPA;
import ar.edu.utn.frba.dds.trayectos.servicios.ddstpa.ServicioDDSTPA;

import java.io.IOException;

public class consumoAPI {
    public static void main(String[] args) {
        AdaptadorServicioDDSTPA servicioExterno = new AdaptadorServicioDDSTPA();
        try {
            System.out.println("OBTENER PAISES");
            servicioExterno.imprimirPaises();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\nOBTENER PROVINCIAS");
            servicioExterno.imprimirProvincias(9);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\nOBTENER MUNICIPIOS 168 BS AS");
            servicioExterno.imprimirMunicipios(168);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\nOBTENER LOCALIDADES 335 AVELLANEDA");
            servicioExterno.imprimirLocalidades(335);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\n\nOBTENER ID LOCALIDAD");
            System.out.println(servicioExterno.obtenerIdLocalidad("DOCK SUD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
