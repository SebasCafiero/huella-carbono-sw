package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.dto.NuevoTrayectoDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TrayectoCompartidoDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.CharacterIterator;
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

    public static TrayectoCompartidoDTO toTrayectoCompartidoDTO(TramoCSVDTO tramo) {
        TrayectoCompartidoDTO trayectoCompartidoDTO = new TrayectoCompartidoDTO();

        trayectoCompartidoDTO.setTrayectoReferencia(Integer.parseInt(tramo.getIdCompartido().trim()));
        trayectoCompartidoDTO.setMiembroDNI(Integer.parseInt(tramo.getMiembroDNI().trim()));

        return trayectoCompartidoDTO;
    }

    public static NuevoTrayectoDTO toNuevoTrayectoDTO(TramoCSVDTO tramo) {
        NuevoTrayectoDTO nuevoTrayectoDTO = new NuevoTrayectoDTO();

        nuevoTrayectoDTO.setTrayectoId(Integer.parseInt(tramo.getTrayectoId().trim()));
        nuevoTrayectoDTO.setMiembroDNI(Integer.parseInt(tramo.getMiembroDNI().trim()));
        nuevoTrayectoDTO.setCompartidoPasivo(!tramo.getIdCompartido().trim().equals("0"));
        nuevoTrayectoDTO.setLatitudInicial(Float.parseFloat(tramo.getLatitudInicial().trim()));
        nuevoTrayectoDTO.setLongitudInicial(Float.parseFloat(tramo.getLongitudInicial().trim()));
        nuevoTrayectoDTO.setLatitudFinal(Float.parseFloat(tramo.getLatitudFinal().trim()));
        nuevoTrayectoDTO.setLongitudFinal(Float.parseFloat(tramo.getLongitudFinal().trim()));
        nuevoTrayectoDTO.setTipoMedio(tramo.getTipo().trim());
        nuevoTrayectoDTO.setAtributo1(tramo.getAtributo1().trim());
        nuevoTrayectoDTO.setAtributo2(tramo.getAtributo2().trim());
        nuevoTrayectoDTO.setPeriodicidad(tramo.getPeriodicidad().charAt(0));
        String[] periodo = tramo.getFecha().split("/");
        nuevoTrayectoDTO.setAnio(Integer.parseInt(periodo[1]));
        nuevoTrayectoDTO.setMes(Integer.parseInt(periodo[0]));

        return nuevoTrayectoDTO;
    }
}

