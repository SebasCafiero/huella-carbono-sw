package ar.edu.utn.frba.dds.interfaces.gui.mappers;

import ar.edu.utn.frba.dds.interfaces.gui.dto.MediosHBS;
import ar.edu.utn.frba.dds.entities.transportes.*;

import java.util.List;
import java.util.stream.Collectors;

public class MediosMapperHBS {
    public static MediosHBS toDTO(Class<? extends MedioDeTransporte> tipoTransporte, List<MedioDeTransporte> medios) {
        MediosHBS mediosDTO = toDTOLazy(tipoTransporte, medios);
        List<MedioDeTransporte> mediosFiltrados = medios.stream().filter(tipoTransporte::isInstance).collect(Collectors.toList());
        mediosDTO.setTransportes(mediosFiltrados.stream().map(TransporteMapperHBS::toDTO).collect(Collectors.toList()));
        return mediosDTO;
    }

    public static MediosHBS toDTOLazy(Class<? extends MedioDeTransporte> tipoTransporte, List<MedioDeTransporte> medios) {
        List<MedioDeTransporte> mediosFiltrados = medios.stream().filter(tipoTransporte::isInstance).collect(Collectors.toList());
        String tipo = null;

        if(tipoTransporte.equals(TransportePublico.class)) tipo = "Públicos";
        else if(tipoTransporte.equals(VehiculoParticular.class)) tipo = "Particulares";
        else if(tipoTransporte.equals(ServicioContratado.class)) tipo = "Contratados";
        else if(tipoTransporte.equals(TransporteEcologico.class)) tipo = "Ecológicos";

        MediosHBS mediosDTO = new MediosHBS();
        mediosDTO.setEsPublico(tipoTransporte.equals(TransportePublico.class));
        mediosDTO.setTipo(tipo);
        mediosDTO.setTransportes(mediosFiltrados.stream().map(TransporteMapperHBS::toDTOLazy).collect(Collectors.toList())); //todo ver de abstraer y reutilizar el toDTO
        return mediosDTO;
    }
}

