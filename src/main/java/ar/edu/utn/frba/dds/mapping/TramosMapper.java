package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO2;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaMedios;
import ar.edu.utn.frba.dds.mihuella.fachada.NoExisteMedioException;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static Tramo toEntity(TramoCSVDTO2 tramoDTO) {
        Repositorio<MedioDeTransporte> repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
        MedioDeTransporte medioSolicitado = new FachadaMedios().obtenerMedio(tramoDTO.getTipoTransporte(), tramoDTO.getSubtipoTransporte(), tramoDTO.getInfo());

        if(medioSolicitado == null) {
            throw new NoExisteMedioException(tramoDTO.getTipoTransporte(), tramoDTO.getSubtipoTransporte(), tramoDTO.getInfo());
        }

        Tramo tramo = new Tramo(medioSolicitado,
                new Coordenada(tramoDTO.getLatInicial(), tramoDTO.getLongInicial()),
                new Coordenada(tramoDTO.getLatFinal(), tramoDTO.getLongFinal()));

        return tramo;
    }

    public static Map.Entry<Integer, Trayecto> modelarTrayecto(List<TramoCSVDTO2> tramosDTODeUnTrayecto) {
        if(!tramosDTODeUnTrayecto.stream().findFirst().isPresent()) throw new RuntimeException("Error al parsear tramo"); //Nunca deberia obtenerse del CSV un trayecto que no tenga un tramoDTO...
        TramoCSVDTO2 unTramoDTO = tramosDTODeUnTrayecto.stream().findFirst().get();
        Trayecto unTrayecto = new Trayecto(PeriodoMapper.toEntity(unTramoDTO.getPeriodicidad(), unTramoDTO.getFecha()));

        Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);
        Optional<Miembro> posibleMiembro = repoMiembros.buscarTodos().stream().filter(m -> m.getNroDocumento() == unTramoDTO.getIdMiembro()).findFirst();
        if(!posibleMiembro.isPresent()) throw new RuntimeException("Miembro Inexistente");
        unTrayecto.agregarMiembro(posibleMiembro.get());
        return new AbstractMap.SimpleEntry<>(unTramoDTO.getIdTrayecto(), unTrayecto);
    }

    public static void mapTrayectoCompartido(Map<Integer, Trayecto> trayectosSegunId, TramoCSVDTO2 tramoDTOCompartido) {
        if(trayectosSegunId.containsKey(tramoDTOCompartido.getIdTrayectoCompartido())) {
            Trayecto trayectoCompartido = trayectosSegunId.get(tramoDTOCompartido.getIdTrayectoCompartido());
            Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);
            Optional<Miembro> posibleMiembro = repoMiembros.buscarTodos().stream().filter(m -> m.getNroDocumento() == tramoDTOCompartido.getIdMiembro()).findFirst();
            if (!posibleMiembro.isPresent()) throw new RuntimeException("Miembro Inexistente");
            trayectoCompartido.agregarMiembro(posibleMiembro.get());
            posibleMiembro.get().agregarTrayecto(trayectoCompartido);
        } else {
            throw new RuntimeException("Trayecto Compartido Inexistente");
        }
    }
}
