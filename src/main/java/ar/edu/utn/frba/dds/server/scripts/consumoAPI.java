package ar.edu.utn.frba.dds.server.scripts;

import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.Municipio;
import ar.edu.utn.frba.dds.entities.lugares.Provincia;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.AdaptadorServicioDDSTPA;

import static java.lang.System.exit;

public class consumoAPI {
    public static void main(String[] args) {
        AdaptadorServicioDDSTPA servicioExterno = new AdaptadorServicioDDSTPA();

        System.out.println("OBTENER PAISES");
        servicioExterno.imprimirPaises();

        System.out.println("\nOBTENER PROVINCIAS de 9-Argentina");
        servicioExterno.imprimirProvincias(9);

        System.out.println("\nOBTENER MUNICIPIOS de 168-BsAs");
        servicioExterno.imprimirMunicipios(168);

        System.out.println("\nOBTENER LOCALIDADES de 335-Avellaneda");
        servicioExterno.imprimirLocalidades(335);

        System.out.println("\n\nOBTENER ID LOCALIDAD DockSud (es 3319)");
//            System.out.println(servicioExterno.obtenerIdLocalidad(new Direccion("Dock Sud","callecita",5))); //TODO PROBAR AL MANDAR VALOR INCORRECTO
        System.out.println(servicioExterno.obtenerIdLocalidad(new Direccion("Argentina", "Buenos Aires", "Avellaneda", "Dock Sud","callecita",5)));

        System.out.println("\n\nOBTENER ID LOCALIDAD La Boca (es 5346)");
        Municipio caba = new Municipio("Ciudad de Buenos Aires", new Provincia("Ciudad de Buenos Aires", "Argentina"));
        System.out.println(servicioExterno.obtenerIdLocalidad(new Direccion(caba, "La Boca", "Brandsen", 805)));
        exit(0);
    }
}
