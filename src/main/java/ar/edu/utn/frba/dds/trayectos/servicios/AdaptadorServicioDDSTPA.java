package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.trayectos.servicios.ddstpa.ServicioDDSTPA;

import java.io.IOException;

public class AdaptadorServicioDDSTPA implements CalculadoraDistancias{
    //SERIA EL ROL ADAPTADOR DEL PATRON ADAPTER PARA EL SERVICIO DDSTPA

    private ServicioDDSTPA servicioExterno = ServicioDDSTPA.getInstancia();


    @Override
    public Float calcularDistancia(Coordenada coordenadaInicial, Coordenada coordenadaFinal) {

        //TODO CONVERTIR COORDENADAS EN DATOS QUE NECESITA LA API
        int idLocalidadOrigen = 1;
        String calleOrigen = "maipu";
        int alturaOrigen = 100;
        int idLocalidadDestino = 457;
        String calleDestino = "O'Higgins";
        int alturaDestino = 200;

        Float miDistancia = 0F;
        try {
            miDistancia = servicioExterno.distancia(idLocalidadOrigen,
                    calleOrigen,
                    alturaOrigen,
                    idLocalidadDestino,
                    calleDestino,
                    alturaDestino).valor;
        } catch (IOException e) {
            System.out.println("ERROR API");
            e.printStackTrace();
        }
        System.out.println("DistanciaConAPI: " + miDistancia); // LA API DEVUELVE DISTANCIAS RANDOM...

        //return servicioExterno.distancia(latitudInicial,longitudInicial,latitudFinal,longitudFinal); //Devuelve unidad KM
        return miDistancia;
    }
}
