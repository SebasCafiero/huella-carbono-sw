package ar.edu.utn.frba.dds.trayectos.servicios.ddstpa;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface OperacionesDDSTPA {

//    @POST("user")
//    Call<Distancia> crearUser()

//    @Headers({"Authorization: Bearer moiMXLBahQOFzlNXcvEQVbkX6vOkHQOWuIx4sXdEEIE="})
    @GET("distancia")
    Call<Distancia> distancia(@Header("Authorization") String token,
                              @Query("localidadOrigenId") int localidadOrigenId,
                              @Query("calleOrigen") String calleOrigen,
                              @Query("alturaOrigen") int alturaOrigen,
                              @Query("localidadDestinoId") int localidadDestinoId,
                              @Query("calleDestino") String calleDestino,
                              @Query("alturaDestino") int alturaDestino);

    @GET("provincias?offset=1&paisId=9")
    Call<List<Provincia>> provincias(@Header("Authorization") String token);
}
