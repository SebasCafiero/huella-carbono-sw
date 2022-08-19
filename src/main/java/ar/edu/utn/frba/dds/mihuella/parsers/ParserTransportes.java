package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.MedioFactory;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParserTransportes {
    private final Repositorio<MedioDeTransporte> repoMedios;

    public ParserTransportes() {
        this.repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
    }

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
            String atr2 = "";

            if(tipoMedio.equals("publico")) {
                atr2 = transporte.getString("linea");
            } else if(tipoMedio.equals("particular")) {
                atr2 = transporte.getString("combustible");
            } else if(!tipoMedio.equals("ecologico") && !tipoMedio.equals("contratado")) {
                throw new Exception(tipoMedio);
            }

            medioDeTransporte = new MedioFactory().getMedioDeTransporte(tipoMedio, subtipoMedio, atr2);

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
            repoMedios.agregar(medioDeTransporte);
        }
        return medios;
    }
}
