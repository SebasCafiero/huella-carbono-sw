package ar.edu.utn.frba.dds.servicios.fachadas;

import ar.edu.utn.frba.dds.entities.lugares.*;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.repositories.Repositorio;
import ar.edu.utn.frba.dds.repositories.utils.FactoryRepositorio;
import ar.edu.utn.frba.dds.servicios.clients.exceptions.ApiGeocodingException;
import ar.edu.utn.frba.dds.servicios.clients.geocoding.Geocoding;
import ar.edu.utn.frba.dds.servicios.clients.geocoding.GeocodingFactory;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class FachadaUbicaciones {
    private final Repositorio<AreaSectorial> repoAreas;
    private final Repositorio<Municipio> repoMunicipios;
    private final Repositorio<Provincia> repoProvincias;
    private final Repositorio<UbicacionGeografica> repoUbicaciones;
    private final Geocoding geocoding;

    public FachadaUbicaciones() {
        this.repoAreas = FactoryRepositorio.get(AreaSectorial.class);
        this.repoMunicipios = FactoryRepositorio.get(Municipio.class);
        this.repoProvincias = FactoryRepositorio.get(Provincia.class);
        this.repoUbicaciones = FactoryRepositorio.get(UbicacionGeografica.class);
        this.geocoding = GeocodingFactory.get();
    }

    public Optional<Municipio> getMunicipio(String municipio, String provincia) {
        return this.repoMunicipios.buscarTodos().stream()
                .filter(muni -> muni.getNombre().equals(municipio) &&
                        provincia.equals(muni.getProvincia().getNombre())).findFirst();
    }

    public Optional<Parada> getParada(TransportePublico transporte, Integer id) {
        return transporte.getParadas().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

//    public UbicacionGeografica getUbicacion(String pais, String provincia, String municipio, String localidad, String calle, Integer numero, Float latitud, Float longitud) {
//        Municipio municipioReal = getMunicipio(municipio, provincia)
//                .orElseThrow(() -> new EntityNotFoundException("No existe la ubicación de municipio " + municipio +
//                        " y provincia " + provincia));
//        Direccion direccion = new Direccion(municipioReal, localidad, calle, numero);
//        Coordenada coordenadas = new Coordenada(latitud, longitud);
//        return this.repoUbicaciones.buscarTodos()
//                .stream()
//                .filter(u -> u.getDireccion().esIgualAOtraDireccion(direccion) && u.getCoordenada().esIgualAOtraCoordenada(coordenadas)) //todo check la igualacion de direcciones
//                .findFirst()
//                .orElseGet(() -> new UbicacionGeografica(direccion, coordenadas));
//    }

    public UbicacionGeografica getUbicacion(String pais, String provincia, String municipio, String localidad, String calle, Integer numero) {
        Municipio municipioReal = getMunicipio(municipio, provincia)
                .orElseThrow(() -> new EntityNotFoundException("No existe la ubicación de municipio " + municipio +
                        " y provincia " + provincia));
        Direccion direccion = new Direccion(municipioReal, localidad, calle, numero);

        UbicacionGeografica ubicacion = geocoding.getUbicacionFromDireccion(direccion)
                .orElseThrow(() -> new ApiGeocodingException(direccion));

        return this.repoUbicaciones.buscarTodos()
                .stream()
                .filter(u -> u.getCoordenada().esIgualAOtraCoordenada(ubicacion.getCoordenada())) // todo check la igualacion de direcciones
                .findFirst()
                .orElse(ubicacion);
    }

}
