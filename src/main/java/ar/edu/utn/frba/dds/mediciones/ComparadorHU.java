package ar.edu.utn.frba.dds.mediciones;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.mediciones.CalculadoraHCOrganizacion;
import java.util.List;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class ComparadorHU {
    private void comparar(Organizacion organizacion1, Organizacion organizacion2, int anio){
        List<Medicion> aux1 = new ArrayList<>();
        List<Medicion> aux2 = new ArrayList<>();
        CalculadoraHCOrganizacion calculadora = new CalculadoraHCOrganizacion();
        aux1=organizacion1.getMediciones().stream().filter( x -> x.getFecha().getYear()==anio).collect(Collectors.toList());
        aux2=organizacion1.getMediciones().stream().filter( x -> x.getFecha().getYear() == anio).collect(Collectors.toList());
        Float cont1=0f;
        Float cont2=0f;
        for (int i = 1; i <= 12; i++) {
            Float valor1=calculadora.obtenerHU(aux1.stream().filter( x -> x.getFecha().getMonthValue() == i).collect(Collectors.toList()));
            Float valor2=calculadora.obtenerHU(aux2.stream().filter( x -> x.getFecha().getMonthValue() == i).collect(Collectors.toList()));
            if(valor1>valor2){
                System.out.println("Para el mes"+i+"la empresa con mayor HU es"+organizacion1);
            }else{
                System.out.println("Para el mes"+i+"la empresa con mayor HU es"+organizacion2);
            }
            cont1+=valor1;
            cont2+=valor2;
        }
        if(cont1>cont2){
            System.out.println("Para el año"+anio+"la empresa con mayor HU es"+organizacion1);
        }else{
            System.out.println("Para el año"+anio+"la empresa con mayor HU es"+organizacion2);
        }

    }



    }

