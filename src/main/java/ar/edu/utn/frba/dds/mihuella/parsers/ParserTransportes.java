package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.transportes.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParserTransportes {
    public List<MedioDeTransporte> cargarTransportes(String archivo) throws Exception {
        String transportesJSON = new JSONParser().parse(new FileReader(archivo)).toString();
        System.out.println("Carga de Transportes:\n" + transportesJSON);

        List<MedioDeTransporte> medios = new ArrayList<>();

        JSONArray arrayTransporte = new JSONArray(transportesJSON);
        for (int orgIndex = 0; orgIndex < arrayTransporte.length(); orgIndex++) {
            JSONObject transporte = arrayTransporte.getJSONObject(orgIndex);
            MedioDeTransporte medioDeTransporte;
            String tipoMedio = transporte.getString("tipo");
            String subtipoMedio = transporte.getString("subtipo");

            if(tipoMedio.equals("contratado")) {
                medioDeTransporte = new ServicioContratado(new TipoServicio(subtipoMedio));
            } else if(tipoMedio.equals("publico")) {
                medioDeTransporte = new TransportePublico(
                        TipoTransportePublico.valueOf(subtipoMedio),
                        transporte.getString("linea")
                );
            } else if(tipoMedio.equals("ecologico")) {
                medioDeTransporte = new TransporteEcologico(
                        TipoTransporteEcologico.valueOf(subtipoMedio));
            } else /*if(tipoMedio.equals("particular"))*/ {
                medioDeTransporte = new VehiculoParticular(
                        TipoVehiculo.valueOf(subtipoMedio),
                        TipoCombustible.valueOf(transporte.getString("combustible"))
                );
            }

            if(tipoMedio.equals("publico")) {
                JSONArray arrayParadas = transporte.getJSONArray("paradas");
                for (int paradaIndex = 0; paradaIndex < arrayParadas.length(); paradaIndex++) {
                    JSONObject parada = arrayParadas.getJSONObject(paradaIndex);

                    ((TransportePublico) medioDeTransporte).agregarParada(new Parada(
                            new Coordenada(parada.getFloat("latitud"), parada.getFloat("longitud")),
                            parada.getFloat("distanciaAnterior"),
                            parada.getFloat("distanciaAnterior"))
                    );

                }
            }
            medios.add(medioDeTransporte);
        }
        return medios;
    }
}
