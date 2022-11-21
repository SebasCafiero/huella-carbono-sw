package ar.edu.utn.frba.dds.interfaces.mappers;

import ar.edu.utn.frba.dds.servicios.fachadas.exceptions.FechaException;
import ar.edu.utn.frba.dds.entities.medibles.Periodo;

public class PeriodoMapper {
    /*public static LocalDate toLocalDate(Character periodicidad, String periodoDTO) {
        if(periodicidad == 'M'){
            String[] mesYanio = periodoDTO.split("/");
            return LocalDate.parse(mesYanio[1]+"-"+mesYanio[0]+"-01");
        }
        else if(periodicidad == 'A'){
            return LocalDate.parse(periodoDTO + "-01-01");
        }
        else {
            throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO
        }
    }*/

    public static Periodo toEntity(Character periodicidad, String fechaDTO) {
        if(periodicidad == 'M') {
            String[] mesYanio = fechaDTO.split("/");
            return new Periodo(Integer.parseInt(mesYanio[1]), Integer.parseInt(mesYanio[0]));
        }
        else if(periodicidad == 'A') {
            return new Periodo(Integer.parseInt(fechaDTO));
        }
        else {
            throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO
        }
    }
}
