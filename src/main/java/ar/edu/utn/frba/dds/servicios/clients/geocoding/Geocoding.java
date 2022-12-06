package ar.edu.utn.frba.dds.servicios.clients.geocoding;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;

import java.util.Optional;

public interface Geocoding {
    Optional<UbicacionGeografica> getUbicacionFromCoordenada(Coordenada coordenada);

    Optional<UbicacionGeografica> getUbicacionFromDireccion(Direccion coordenada);
}
