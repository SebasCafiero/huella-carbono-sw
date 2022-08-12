package ar.edu.utn.frba.dds.mihuella.utils;

import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;

import java.time.LocalDate;

public class PeriodoMapper {
    public static Periodo toEntity(char periodicidad, String fecha) throws FechaException {
        Periodo periodo = new Periodo();

        if(periodicidad == 'M') {
            String[] mesYanio = fecha.split("/");
            periodo.setPeriodicidad('M');
            periodo.setMes(Integer.parseInt(mesYanio[0]));
            periodo.setAnio(Integer.parseInt(mesYanio[1]));
        }
        else if(periodicidad == 'A') {
            periodo.setPeriodicidad('M');
            periodo.setAnio(Integer.parseInt(fecha));
        }
        else {
            throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO
        }

        return periodo;
    }

    public static void validatePeriodo(Periodo periodo) {
        if(periodo.getMes() > 12 || periodo.getMes() < 0) {
            System.out.println("Mes invalido");
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
