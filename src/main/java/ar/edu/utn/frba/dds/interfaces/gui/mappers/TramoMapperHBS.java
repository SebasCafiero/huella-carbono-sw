package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.transportes.Parada;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TramoHBS;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.TransportePublico;
import ar.edu.utn.frba.dds.entities.medibles.Tramo;
import ar.edu.utn.frba.dds.interfaces.gui.dto.TransporteHBS;
import spark.Request;

import java.util.Optional;

public class TramoMapperHBS {

    public static TramoHBS toDTO(Tramo tramo) {
        TramoHBS tramoDTO = toDTOTransporteLazy(tramo);
        tramoDTO.setTransporte(TransporteMapperHBS.toDTO(tramo.getMedioDeTransporte()));
        return tramoDTO;
    }

    public static TramoHBS toDTOTransporteLazy(Tramo tramo) {
        Integer idParadaInicial = null;
        Integer idParadaFinal = null;
        if(tramo.getMedioDeTransporte() instanceof TransportePublico) { //todo check busqueda de idParada
            idParadaInicial = ((TransportePublico) tramo.getMedioDeTransporte()).buscarParada(tramo.getUbicacionInicial().getCoordenada()).getId();
            idParadaFinal = ((TransportePublico) tramo.getMedioDeTransporte()).buscarParada(tramo.getUbicacionFinal().getCoordenada()).getId();
        }

        TramoHBS tramoDTO = new TramoHBS();
        tramoDTO.setId(tramo.getId());
        tramoDTO.setTransporte(TransporteMapperHBS.toDTOLazy(tramo.getMedioDeTransporte()));
        tramoDTO.setUbicacionInicial(UbicacionMapperHBS.toDTO(tramo.getUbicacionInicial()));
        tramoDTO.setUbicacionFinal(UbicacionMapperHBS.toDTO(tramo.getUbicacionFinal()));
        tramoDTO.setIdParadaInicial(idParadaInicial);
        tramoDTO.setIdParadaFinal(idParadaFinal);

        return tramoDTO;
    }

    public static TramoHBS toDTOLazy(MedioDeTransporte transporte) {
        TramoHBS tramoDTO = new TramoHBS();
        tramoDTO.setTransporte(TransporteMapperHBS.toDTOLazy(transporte));
        return tramoDTO;
    }

    public static TramoHBS toDTOEditado(MedioDeTransporte transporte, Integer cant, Request request) {
        return mapTramoEditable(transporte, cant, request); //todo ver de no recibir la request
    }

    private static TramoHBS mapTramoEditable(MedioDeTransporte transporte, Integer cant, Request req) {
        TransporteHBS transporteDTO = TransporteMapperHBS.toDTOLazy(transporte); //deberia sacarlo xq no lo uso casi
        TramoHBS tramoDTO = new TramoHBS();
        UbicacionGeografica ubicacionInicial = null;
        UbicacionGeografica ubicacionFinal = null;
        if(transporteDTO.getEsPublico()) {
            if (req.queryParams("parada-inicial" + cant) != null && req.queryParams("parada-final" + cant) != null) {
                int idParadaInicial = Integer.parseInt(req.queryParams("parada-inicial" + cant));
                int idParadaFinal = Integer.parseInt(req.queryParams("parada-final" + cant));
                Optional<Parada> op_paradaInicial = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idParadaInicial).findFirst();
                Optional<Parada> op_paradaFinal = ((TransportePublico) transporte).getParadas().stream().filter(p -> p.getId() == idParadaFinal).findFirst();

                if (op_paradaInicial.isPresent() && op_paradaFinal.isPresent()) {
                    Parada paradaInicial = op_paradaInicial.get();
                    Parada paradaFinal = op_paradaFinal.get();

                    ubicacionInicial = paradaInicial.getUbicacion();
                    ubicacionFinal = paradaFinal.getUbicacion();

                    tramoDTO.setIdParadaInicial(idParadaInicial);
                    tramoDTO.setIdParadaFinal(idParadaFinal);
                }
            }
        } else {
            if(req.queryParams("lat-inicial"+cant) != null
                    && req.queryParams("lat-final"+cant) != null
                    && req.queryParams("prov-inicial"+cant) != null
                    && req.queryParams("prov-final"+cant) != null) { //uso los primeros de coord y de direc, pero quizas validar todos

                Coordenada coorInicial = new Coordenada(
                        Float.parseFloat(req.queryParams("lat-inicial"+cant)),
                        Float.parseFloat(req.queryParams("lon-inicial"+cant)));
                Coordenada coorFinal = new Coordenada(
                        Float.parseFloat(req.queryParams("lat-final"+cant)),
                        Float.parseFloat(req.queryParams("lon-final"+cant)));

                ubicacionInicial = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("prov-inicial"+cant),
                        req.queryParams("mun-inicial"+cant),
                        req.queryParams("loc-inicial"+cant),
                        req.queryParams("calle-inicial"+cant),
                        Integer.parseInt(req.queryParams("num-inicial"+cant)),
                        coorInicial
                );

                ubicacionFinal = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("prov-final"+cant),
                        req.queryParams("mun-final"+cant),
                        req.queryParams("loc-final"+cant),
                        req.queryParams("calle-final"+cant),
                        Integer.parseInt(req.queryParams("num-final"+cant)),
                        coorFinal
                );
            }
        }
        if(ubicacionInicial != null)
            tramoDTO.setUbicacionInicial(UbicacionMapperHBS.toDTO(ubicacionInicial));
        else
            tramoDTO.setUbicacionInicial(null);

        if(ubicacionFinal != null)
            tramoDTO.setUbicacionFinal(UbicacionMapperHBS.toDTO(ubicacionFinal));
        else
            tramoDTO.setUbicacionFinal(null);

        if(req.queryParams("tramo-id"+cant) != null)
            tramoDTO.setId(Integer.parseInt(req.queryParams("tramo-id"+cant)));

        tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporte));
        return tramoDTO;
    }


    public static TramoHBS toDTONuevoEditable(MedioDeTransporte transporte, Request request) {
        return mapTramoNuevo(transporte, request); //todo ver de no recibir la request
    }

    private static TramoHBS mapTramoNuevo(MedioDeTransporte transporteNuevo, Request req) {
        TramoHBS tramoDTO = new TramoHBS();
        UbicacionGeografica ubicacionInicial = null;
        UbicacionGeografica ubicacionFinal = null;
        if(transporteNuevo instanceof TransportePublico) {
            if (req.queryParams("parada-inicial-nueva") != null && req.queryParams("parada-final-nueva") != null) {
                int idParadaInicial = Integer.parseInt(req.queryParams("parada-inicial-nueva"));
                int idParadaFinal = Integer.parseInt(req.queryParams("parada-final-nueva"));

                Optional<Parada> op_paradaInicial = ((TransportePublico) transporteNuevo).getParadas().stream().filter(p -> p.getId() == idParadaInicial).findFirst();
                Optional<Parada> op_paradaFinal = ((TransportePublico) transporteNuevo).getParadas().stream().filter(p -> p.getId() == idParadaFinal).findFirst();

                if (op_paradaInicial.isPresent() && op_paradaFinal.isPresent()) {
                    Parada paradaInicial = op_paradaInicial.get();
                    Parada paradaFinal = op_paradaFinal.get();

                    ubicacionInicial = paradaInicial.getUbicacion();
                    ubicacionFinal = paradaFinal.getUbicacion();

                    tramoDTO.setIdParadaInicial(idParadaInicial);
                    tramoDTO.setIdParadaFinal(idParadaFinal);
                }
            }
        } else {
            if(req.queryParams("lat-inicial-nueva") != null
                    && req.queryParams("lat-final-nueva") != null
                    && req.queryParams("prov-inicial-nueva") != null
                    && req.queryParams("prov-final-nueva") != null) { //uso los primeros de coord y de direc, pero quizas validar todos

                Coordenada coorInicial = new Coordenada(
                        Float.parseFloat(req.queryParams("lat-inicial-nueva")),
                        Float.parseFloat(req.queryParams("lon-inicial-nueva")));
                Coordenada coorFinal = new Coordenada(
                        Float.parseFloat(req.queryParams("lat-final-nueva")),
                        Float.parseFloat(req.queryParams("lon-final-nueva")));

                ubicacionInicial = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("prov-inicial-nueva"),
                        req.queryParams("mun-inicial-nueva"),
                        req.queryParams("loc-inicial-nueva"),
                        req.queryParams("calle-inicial-nueva"),
                        Integer.parseInt(req.queryParams("num-inicial-nueva")),
                        coorInicial
                );

                ubicacionFinal = new UbicacionGeografica(
                        "Argentina",
                        req.queryParams("prov-final-nueva"),
                        req.queryParams("mun-final-nueva"),
                        req.queryParams("loc-final-nueva"),
                        req.queryParams("calle-final-nueva"),
                        Integer.parseInt(req.queryParams("num-final-nueva")),
                        coorFinal
                );
            }
        }
        if(ubicacionInicial != null)
            tramoDTO.setUbicacionInicial(UbicacionMapperHBS.toDTO(ubicacionInicial));
        else
            tramoDTO.setUbicacionInicial(null);

        if(ubicacionFinal != null)
            tramoDTO.setUbicacionFinal(UbicacionMapperHBS.toDTO(ubicacionFinal));
        else
            tramoDTO.setUbicacionFinal(null);

        tramoDTO.setTransporte(TransporteMapperHBS.toDTO(transporteNuevo));

//        parametros.put("tramoNuevo", tramoDTO);
        return tramoDTO;

    }
}