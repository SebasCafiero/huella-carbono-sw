package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.mihuella.dto.NuevoTrayectoDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TrayectoCompartidoDTO;

public class TrayectosMapper {

    public static TrayectoCompartidoDTO toTrayectoCompartidoDTO(TramoCSVDTO tramo) {
        TrayectoCompartidoDTO trayectoCompartidoDTO = new TrayectoCompartidoDTO();

        trayectoCompartidoDTO.setTipoDocumento(tramo.getTipoDocumento());
        trayectoCompartidoDTO.setMiembroDNI(Integer.parseInt(tramo.getMiembroDNI().trim()));
        trayectoCompartidoDTO.setTrayectoReferencia(Integer.parseInt(tramo.getIdCompartido().trim()));

        return trayectoCompartidoDTO;
    }

    public static NuevoTrayectoDTO toNuevoTrayectoDTO(TramoCSVDTO tramo) {
        NuevoTrayectoDTO nuevoTrayectoDTO = new NuevoTrayectoDTO();

        nuevoTrayectoDTO.setTrayectoId(Integer.parseInt(tramo.getTrayectoId().trim()));
        nuevoTrayectoDTO.setTipoDocumento(tramo.getTipoDocumento().trim());
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

