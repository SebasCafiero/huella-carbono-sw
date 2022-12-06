package ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.servicios.clients.geocoding.Geocoding;
import ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim.dto.UbicacionNominatim;

import java.util.List;
import java.util.Optional;

public class GeocodingNominatimImpl implements Geocoding {
    @Override
    public Optional<UbicacionGeografica> getUbicacionFromCoordenada(Coordenada coordenada) {
        return Optional.empty();
    }

    @Override
    public Optional<UbicacionGeografica> getUbicacionFromDireccion(Direccion direccion) {
        ServicioGeocodingNominatim servicio = ServicioGeocodingNominatim.getInstancia();
        List<UbicacionNominatim> ubicacionResponse = servicio.getUbicacion(
                direccion.getNumero().toString() + direccion.getCalle(), direccion.getLocalidad(), "Argentina");

        if (ubicacionResponse != null && ubicacionResponse.size() == 1) {
            direccion.setCalle(ubicacionResponse.get(0).getRoad());
            direccion.setNumero(Integer.parseInt(ubicacionResponse.get(0).getHouse_number()));

            return Optional.of(new UbicacionGeografica(direccion,
                    new Coordenada(ubicacionResponse.get(0).getLat(), ubicacionResponse.get(0).getLon())));
        }
        return Optional.empty();
    }
}
