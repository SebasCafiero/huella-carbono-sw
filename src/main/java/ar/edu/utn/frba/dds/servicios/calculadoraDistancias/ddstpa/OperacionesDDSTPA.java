package ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ddstpa;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface OperacionesDDSTPA {

//    @POST("user")
//    Call<Distancia> crearUser()

//    @Headers({"Authorization: Bearer moiMXLBahQOFzlNXcvEQVbkX6vOkHQOWuIx4sXdEEIE="})
    @GET("distancia")
    Call<DistanciaGson> distancia(@Header("Authorization") String token,
                                  @Query("localidadOrigenId") int localidadOrigenId,
                                  @Query("calleOrigen") String calleOrigen,
                                  @Query("alturaOrigen") int alturaOrigen,
                                  @Query("localidadDestinoId") int localidadDestinoId,
                                  @Query("calleDestino") String calleDestino,
                                  @Query("alturaDestino") int alturaDestino);

    @GET("provincias?offset=1")
    Call<List<ProvinciaGson>> provincias(@Header("Authorization") String token,
                                         @Query("paisId") int paisId);

    @GET("paises?offset=1")
    Call<List<PaisGson>> paises(@Header("Authorization") String token);

    @GET("municipios")
    Call<List<MunicipioGson>> municipios(@Header("Authorization") String token,
                                         @Query("offset") int pagina,
                                         @Query("provinciaId") int provinciaId);

    @GET("localidades?offset=1")
    Call<List<LocalidadGson>> localidades(@Header("Authorization") String token,
                                          @Query("municipioId") int municipioId);
}
