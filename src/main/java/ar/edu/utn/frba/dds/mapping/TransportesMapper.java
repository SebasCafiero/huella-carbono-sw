package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.transportes.*;
import ar.edu.utn.frba.dds.mihuella.dto.TransporteJSONDTO;

public class TransportesMapper {
    public static MedioDeTransporte toEntity(TransporteJSONDTO transporteDTO) {
        MedioDeTransporte transporte;

        String tipoTransporte = transporteDTO.tipo;
        if(tipoTransporte.equals("publico")) {
            TransportePublico transportePublico = new TransportePublico(
                    TipoTransportePublico.valueOf(transporteDTO.subtipo.toUpperCase()),
                    transporteDTO.linea
            );
            for(TransporteJSONDTO.ParadaDTO paradaDTO : transporteDTO.paradas) {
                transportePublico.agregarParada(new Parada(new Direccion(),
                        new Coordenada(paradaDTO.latitud, paradaDTO.longitud),
                        paradaDTO.distanciaAnterior,
                        paradaDTO.distanciaProxima
                ));
            }
            transporte = transportePublico;
        } else {
            if(tipoTransporte.equals("particular")) {
                transporte = new VehiculoParticular(
                        TipoVehiculo.valueOf(transporteDTO.subtipo.toUpperCase()),
                        TipoCombustible.valueOf(transporteDTO.combustible)
                );
            } else {
                if(tipoTransporte.equals("ecologico")) {
                    transporte = new TransporteEcologico(TipoTransporteEcologico.valueOf(transporteDTO.subtipo.toUpperCase()));
                } else {
                    if(tipoTransporte.equals("contratado")) {
                        transporte = new ServicioContratado(new TipoServicio(transporteDTO.subtipo.toUpperCase()));
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
