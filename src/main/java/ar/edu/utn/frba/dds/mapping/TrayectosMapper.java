package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrayectosMapper {

    public static void map(JSONArray trayectosDTO, List<Trayecto> trayectos){

        trayectosDTO.forEach(itemTrayecto -> {
            JSONObject trayectoDTO = (JSONObject) itemTrayecto;

            Trayecto trayecto = new Trayecto();
            List<Tramo> tramos = new ArrayList<>();

            TramosMapper.map(trayectoDTO.optJSONArray("tramos"), tramos);

            trayecto.setId(trayectoDTO.optInt("id"));
            trayecto.setTramos(tramos);
        });

    }
}

