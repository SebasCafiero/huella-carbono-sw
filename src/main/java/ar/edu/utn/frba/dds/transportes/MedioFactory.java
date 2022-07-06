package ar.edu.utn.frba.dds.transportes;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import org.json.JSONArray;
import org.json.JSONObject;

public class MedioFactory {

    public MedioDeTransporte getMedioDeTransporte(String tipo, String subtipo, String atr2) throws Exception {
        MedioDeTransporte medio;
        if(tipo.equals("contratado")) {
            medio = new ServicioContratado(new TipoServicio(subtipo.toUpperCase()));
        } else if(tipo.equals("publico")) {
            medio = new TransportePublico(TipoTransportePublico.valueOf(subtipo.toUpperCase()), atr2);
        } else if(tipo.equals("ecologico")) {
            medio = new TransporteEcologico(TipoTransporteEcologico.valueOf(subtipo.toUpperCase()));
        } else if(tipo.equals("particular")) {
            medio = new VehiculoParticular(TipoVehiculo.valueOf(subtipo.toUpperCase()), TipoCombustible.valueOf(atr2));
        } else {
            throw new Exception();
        }
        return medio;
//        if(tipo.equals("publico")) {
//            JSONArray arrayParadas = transporte.getJSONArray("paradas");
//            for (int paradaIndex = 0; paradaIndex < arrayParadas.length(); paradaIndex++) {
//                JSONObject parada = arrayParadas.getJSONObject(paradaIndex);
//
//                ((TransportePublico) medio).agregarParada(new Parada(
//                        new Coordenada(parada.getFloat("latitud"), parada.getFloat("longitud")),
//                        parada.getFloat("distanciaAnterior"),
//                        parada.getFloat("distanciaAnterior"))
//                );
//
//            }
//        }
    }

}
