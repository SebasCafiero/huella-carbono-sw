package ar.edu.utn.frba.dds.personas;

import ar.edu.utn.frba.dds.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.mediciones.Reporte;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.trayectos.Trayecto;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AgenteSectorial {
    private AreaSectorial area;
    private String mail;
    private String telefono;
    private Integer periodo;
    private List<Reporte> reportes;

    public Float obtenerHC(Integer anio, Integer mes) throws MedicionSinFactorEmisionException {
        return obtenerHcxOrg(anio, mes).values().stream().reduce(0F, Float::sum);
    }

    public HashMap<Organizacion,Float> obtenerHcxOrg(Integer anio, Integer mes) throws MedicionSinFactorEmisionException {
        FachadaOrganizacion fachada = new FachadaOrganizacion();
        HashMap<Organizacion,Float> resultados = new HashMap<>();

        for(Organizacion organizacion : area.getOrganizaciones()) {
            List<Medible> mediciones = organizacion.getMediciones().stream()
                    .filter(me -> perteneceAPeriodo(me, anio, mes))
                    .collect(Collectors.toList());
            List<Medible> tramos = organizacion.miembros().stream()
                    .flatMap(mi -> mi.getTrayectos().stream())
                    .filter(tr -> perteneceAPeriodo(tr, anio, mes))
                    .flatMap(tr -> tr.getTramos().stream())
                    .collect(Collectors.toList());

            mediciones.addAll(tramos);
            Float valor = fachada.obtenerHU(mediciones);
            resultados.put(organizacion, valor);
        }
        return resultados;
    }

    public Reporte crearReporte(Integer anio, Integer mes) throws MedicionSinFactorEmisionException {
//        LocalDate fecha = LocalDate.now();
        return new Reporte(area.getOrganizaciones(), this.obtenerHcxOrg(anio, mes), area, this.obtenerHC(anio,mes));
    }

    public void enviarReporte(){

    }

    private boolean perteneceAPeriodo(Medicion medicion, Integer anio, Integer mes) {
        if(medicion.getPeriodicidad().equals("A")) {
            return medicion.getAnio().equals(anio);
        } else if(medicion.getPeriodicidad().equals("M")) {
            return medicion.getAnio().equals(anio) && medicion.getMes().equals(mes);
        }
        return false;
    }

    private boolean perteneceAPeriodo(Trayecto trayecto, Integer anio, Integer mes) {
        if(trayecto.getPeriodicidad().equals("A")) {
            return trayecto.getAnio().equals(anio);
        } else if(trayecto.getPeriodicidad().equals("M")) {
            return trayecto.getAnio().equals(anio) && trayecto.getMes().equals(mes);
        }
        return false;
    }
}
