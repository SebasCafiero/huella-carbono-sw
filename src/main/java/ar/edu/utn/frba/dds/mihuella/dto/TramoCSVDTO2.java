package ar.edu.utn.frba.dds.mihuella.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.processor.PreAssignmentProcessor;

public class TramoCSVDTO2 {

    @CsvBindByName(column = "TrayectoId")
    public int idTrayecto;

    @CsvBindByName(column = "MiembroDNI")
    public int idMiembro;

    @CsvBindByName(column = "IdCompartido")
    public int idTrayectoCompartido;

    @CsvBindByName(column = "LatitudInicial")
    public float latInicial;

    @CsvBindByName(column = "LongitudInicial")
    public float longInicial;

    @CsvBindByName(column = "LatitudFinal")
    public float latFinal;

    @CsvBindByName(column = "LongitudFinal")
    public float longFinal;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Tipo")
    public String tipoTransporte;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Atributo1")
    public String subtipoTransporte;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Atributo2")
    public String info;

    @CsvBindByName(column = "Periodicidad")
    public char periodicidad;

    @PreAssignmentProcessor(processor = ProcesarEspacios.class)
    @CsvBindByName(column = "Fecha")
    public String fecha;

    public TramoCSVDTO2() {

    }

    @Override
    public String toString() {
        return "TramoCSVDTO2{" +
                "idTrayecto=" + idTrayecto +
                ", idMiembro=" + idMiembro +
                ", idTrayectoCompartido=" + idTrayectoCompartido +
                ", latInicial=" + latInicial +
                ", longInicial=" + longInicial +
                ", latFinal=" + latFinal +
                ", longFinal=" + longFinal +
                ", tipoTransporte='" + tipoTransporte + '\'' +
                ", subtipoTransporte='" + subtipoTransporte + '\'' +
                ", info='" + info + '\'' +
                ", periodicidad=" + periodicidad +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    //CsvToBeanBuilder.withOrderedResults(false)
}
