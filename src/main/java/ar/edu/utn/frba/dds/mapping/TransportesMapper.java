package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.mihuella.dto.TransporteDTO;

public class TransportesMapper {
    public static MedioDeTransporte toEntity(TransporteDTO transporteDTO) {
        MedioDeTransporte transporte;

        String tipoTransporte = transporteDTO.tipo;
        if(tipoTransporte.equals("publico")) {
            TransportePublico transportePublico = new TransportePublico(
                    TipoTransportePublico.valueOf(transporteDTO.subtipo),
                    transporteDTO.linea
            );
            for(TransporteDTO.ParadaDTO paradaDTO : transporteDTO.paradas) {
                transportePublico.agregarParada(new Parada(
                        new Coordenada(paradaDTO.latitud, paradaDTO.longitud),
                        paradaDTO.distanciaAnterior,
                        paradaDTO.distanciaProxima
                ));
            }
            transporte = transportePublico;
        } else {
            if(tipoTransporte.equals("particular")) {
                transporte = new VehiculoParticular(
                        TipoVehiculo.valueOf(transporteDTO.subtipo),
                        TipoCombustible.valueOf(transporteDTO.combustible)
                );
            } else {
                if(tipoTransporte.equals("ecologico")) {
                    transporte = new TransporteEcologico(TipoTransporteEcologico.valueOf(transporteDTO.subtipo));
                } else {
                    if(tipoTransporte.equals("contratado")) {
                        transporte = new ServicioContratado(new TipoServicio(transporteDTO.subtipo));
                    } else {
                        throw new RuntimeException("FUCKING TIPO DE TRANSPORTE");
                    }
                }
            }
        }
        //agregar nombre a cada medio de transporte TODO
        return transporte;
    }
}
