package ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa;

import ar.edu.utn.frba.dds.server.SystemProperties;
import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.ddstpa.dto.*;
import ar.edu.utn.frba.dds.servicios.clients.exceptions.ApiDistanciasException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioDDSTPA {
    //SERIA EL ROL ADAPTABLE DEL PATRON ADAPTER PARA EL SERVICIO DDSTPA
    //https://app.swaggerhub.com/apis-docs/ezequieloscarescobar/geodds/1.0.0

    private static final String URL_ABS = SystemProperties.getCalculadoraDistanciasUrl();
    private static final String TOKEN = SystemProperties.getCalculadoraDistanciasToken();

    private static ServicioDDSTPA instancia = null;
    private Retrofit retrofit;

    private ServicioDDSTPA() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL_ABS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioDDSTPA getInstancia() {
        if(instancia == null) instancia = new ServicioDDSTPA();
        return instancia;
    }

    public DistanciaGson distancia(int idLocalidadOrigen, String calleOrigen, int alturaOrigen, int idLocalidadDestino, String calleDestino, int alturaDestino) {

        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<DistanciaGson> requestDistancia = operacionesDDSTPA.distancia(
                TOKEN,
                idLocalidadOrigen,
                calleOrigen,
                alturaOrigen,
                idLocalidadDestino,
                calleDestino,
                alturaDestino);

        return this.executeRequest(requestDistancia).body();

    }

    public List<ProvinciaGson> provincias(int idPais) {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<ProvinciaGson>> requestProvincias = operacionesDDSTPA.provincias(TOKEN, idPais);

        return this.executeRequest(requestProvincias).body();
    }

    public List<MunicipioGson> municipios(int idProvincia) {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<MunicipioGson>> requestMunicipios = operacionesDDSTPA.municipios(TOKEN, 1, idProvincia); //TODO RECORRER TODAS LAS PAGINAS

        return this.executeRequest(requestMunicipios).body();
    }

    public List<LocalidadGson> localidades(int idMunicipio) {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<LocalidadGson>> requestLocalidades = operacionesDDSTPA.localidades(TOKEN,idMunicipio);

        return this.executeRequest(requestLocalidades).body();
    }

    public List<PaisGson> paises() {
        OperacionesDDSTPA operacionesDDSTPA = this.retrofit.create(OperacionesDDSTPA.class);
        Call<List<PaisGson>> requestPaises = operacionesDDSTPA.paises(TOKEN);

        return this.executeRequest(requestPaises).body();
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
