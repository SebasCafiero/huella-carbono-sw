package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.AdaptadorServicioDDSTPA;

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
            System.out.println("\nOBTENER PROVINCIAS de 9-Argentina");
            servicioExterno.imprimirProvincias(9);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\nOBTENER MUNICIPIOS de 168-BsAs");
            servicioExterno.imprimirMunicipios(168);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\nOBTENER LOCALIDADES de 335-Avellaneda");
            servicioExterno.imprimirLocalidades(335);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\n\nOBTENER ID LOCALIDAD DockSud (es 3319)");
//            System.out.println(servicioExterno.obtenerIdLocalidad(new Direccion("Dock Sud","callecita",5))); //TODO PROBAR AL MANDAR VALOR INCORRECTO
            System.out.println(servicioExterno.obtenerIdLocalidad(new Direccion("Argentina", "Buenos Aires", "Avellaneda", "Dock Sud","callecita",5)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("\n\nOBTENER ID LOCALIDAD La Boca (es 5346)");
            System.out.println(servicioExterno.obtenerIdLocalidad(new Direccion())); //Por defecto CABA-La Boca
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
