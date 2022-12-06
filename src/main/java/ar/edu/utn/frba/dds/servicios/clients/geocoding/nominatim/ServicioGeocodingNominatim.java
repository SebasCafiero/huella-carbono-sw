package ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim;

import ar.edu.utn.frba.dds.server.SystemProperties;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.OperacionesDDSTPA;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.dto.*;
import ar.edu.utn.frba.dds.servicios.clients.exceptions.ApiDistanciasException;
import ar.edu.utn.frba.dds.servicios.clients.geocoding.nominatim.dto.UbicacionNominatim;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.List;

public class ServicioGeocodingNominatim {
    //https://app.swaggerhub.com/apis-docs/ezequieloscarescobar/geodds/1.0.0

    private static final String URL_ABS = "https://nominatim.openstreetmap.org/search";

    private static ServicioGeocodingNominatim instancia = null;
    private Retrofit retrofit;

    private ServicioGeocodingNominatim() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL_ABS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeocodingNominatim getInstancia() {
        if(instancia == null) instancia = new ServicioGeocodingNominatim();
        return instancia;
    }

    public List<UbicacionNominatim> getUbicacion(String direccion, String localidad, String pais) {
        OperacionesGeocodingNominatim operaciones = this.retrofit.create(OperacionesGeocodingNominatim.class);
        Call<List<UbicacionNominatim>> requestDistancia = operaciones
                .getUbicacionFromQuery(direccion + localidad + pais, "json", "1");

        return this.executeRequest(requestDistancia).body();
    }

    private <T> Response<T> executeRequest(Call<T> request) {
        Response<T> response;
        System.out.println("REQUEST GET TO " + request.request().url());
        try {
            response = request.execute();
            System.out.println("RESULTADO REQUEST OK");
        } catch (IOException e) {
            System.out.println("RESULTADO REQUEST ERROR - " + e.getMessage());
            throw new ApiDistanciasException(e.getMessage());
        } finally {
            System.out.println("FIN DE REQUEST");
        }
        return response;
    }

}
