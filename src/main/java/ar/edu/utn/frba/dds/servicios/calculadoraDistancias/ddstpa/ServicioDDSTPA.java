package ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ddstpa;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioDDSTPA {
    //SERIA EL ROL ADAPTABLE DEL PATRON ADAPTER PARA EL SERVICIO DDSTPA
    private static final String URL_ABS = "https://ddstpa.com.ar/api/"; //https://app.swaggerhub.com/apis-docs/ezequieloscarescobar/geodds/1.0.0
    private static final String TOKEN = "Bearer moiMXLBahQOFzlNXcvEQVbkX6vOkHQOWuIx4sXdEEIE=";

    private static ServicioDDSTPA instancia = null;
    private Retrofit retrofit;

    private ServicioDDSTPA(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL_ABS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioDDSTPA getInstancia(){
        if(instancia == null) instancia = new ServicioDDSTPA();
        return instancia;
    }

    public Distancia distancia(int idLocalidadOrigen, String calleOrigen, int alturaOrigen, int idLocalidadDestino, String calleDestino, int alturaDestino) throws IOException {

        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<Distancia> requestDistancia = operacionesDDSTPA.distancia(
                TOKEN,
                idLocalidadOrigen,
                calleOrigen,
                alturaOrigen,
                idLocalidadDestino,
                calleDestino,
                alturaDestino);

        Response<Distancia> responseDistancia = requestDistancia.execute();

        return responseDistancia.body();

    }

    public List<ProvinciaGson> provincias(int idPais) throws IOException {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<ProvinciaGson>> requestProvincias = operacionesDDSTPA.provincias(TOKEN, idPais);
        Response<List<ProvinciaGson>> responseProvincias = requestProvincias.execute();
        return responseProvincias.body();
    }

    public List<MunicipioGson> municipios(int idProvincia) throws IOException {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<MunicipioGson>> requestMunicipios = operacionesDDSTPA.municipios(TOKEN, 1, idProvincia); //TODO RECORRER TODAS LAS PAGINAS
        Response<List<MunicipioGson>> responseMunicipios = requestMunicipios.execute();
        return responseMunicipios.body();
    }

    public List<LocalidadGson> localidades(int idMunicipio) throws IOException {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<LocalidadGson>> requestLocalidades = operacionesDDSTPA.localidades(TOKEN,idMunicipio);
        Response<List<LocalidadGson>> responseLocalidades = requestLocalidades.execute();
        return responseLocalidades.body();
    }

    public List<PaisGson> paises() throws IOException {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<PaisGson>> requestPaises = operacionesDDSTPA.paises(TOKEN);
        Response<List<PaisGson>> responsePaises = requestPaises.execute();
        return responsePaises.body();
    }

}
