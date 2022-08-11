package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.transportes.TipoCombustible;
import ar.edu.utn.frba.dds.transportes.TipoVehiculo;
import ar.edu.utn.frba.dds.transportes.VehiculoParticular;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TramosMapper {

    private static <T> T getMedioTransporte(JSONObject jsonObject) {
        switch (jsonObject.optInt("tipo")) {
            case 0:
                return (T) new VehiculoParticular(TipoVehiculo.valueOf(jsonObject.optString("transporte")), TipoCombustible.valueOf(jsonObject.optString("combustible")));
           // case 1:
           //     return (T) Double.valueOf(value);
           // case 2:
           //     return (T) Integer.valueOf(value);
           // case 3:
           //     return (T) Double.valueOf(value);
            default:
                return null;
        }
    }
    public static void map(JSONArray tramosDTO, List<Tramo> tramos){
        tramosDTO.forEach(itemTramo -> {
            JSONObject tramoDTO = (JSONObject) itemTramo;
            JSONObject medioTransporte = tramoDTO.optJSONObject("medioTransporte");
            JSONObject ci = tramoDTO.optJSONObject("coordenadaInicial");
            JSONObject cf = tramoDTO.optJSONObject("coordenadaFinal");

            Tramo tramo = new Tramo(
                    getMedioTransporte(medioTransporte),
                    new Coordenada(ci.optFloat("latitud"), ci.optFloat("longitud")),
                    new Coordenada(cf.optFloat("latitud"), cf.optFloat("longitud"))
            );

            tramos.add(tramo);
        });
    }
}
