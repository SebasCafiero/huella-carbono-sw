package ar.edu.utn.frba.dds.servicios.clients.geocoding;

import ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim.GeocodingNominatimImpl;

public class GeocodingFactory {
    public static Geocoding get() {
        return new GeocodingNominatimImpl();
    }
}
