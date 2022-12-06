package ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim;

import ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim.dto.UbicacionNominatim;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface OperacionesGeocodingNominatim {
    @GET("")
    Call<UbicacionNominatim> getUbicacionFromAddressAndCityAndCountry(
            @Query("streat") String houseNumberAndStreatName,
            @Query("city") String city,
            @Query("country") String pais,
            @Query("format") String format);

    @GET("")
    Call<List<UbicacionNominatim>> getUbicacionFromQuery(
            @Query("q") String query,
            @Query("format") String format,
            @Query("addressdetails") String addressDetails);
}
