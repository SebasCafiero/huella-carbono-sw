package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TrayectosMapper {

    public static void map(JSONArray trayectosDTO, List<Trayecto> trayectos){

        trayectosDTO.forEach(itemTrayecto -> {
            JSONObject trayectoDTO = (JSONObject) itemTrayecto;
            Trayecto trayecto1 = new Trayecto();
        });

    }
}

