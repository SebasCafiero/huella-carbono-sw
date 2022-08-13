package ar.edu.utn.frba.dds.mapping;

import ar.edu.utn.frba.dds.entities.mediciones.FechaException;

import java.time.LocalDate;

public class PeriodoMapper {
    public static LocalDate toLocalDate(Character periodicidad, String periodoDTO) {
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
    }

    //        if(periodicidad == 'M'){
//            String[] mesYanio = fecha.split("/");
//            periodo = LocalDate.parse(mesYanio[1]+"-"+mesYanio[0]+"-01");
//        }
//        else if(periodicidad == 'A'){
//            periodo = LocalDate.parse(data[5] + "-01-01");
//        }
//        else {
//            throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO
//        }

}
