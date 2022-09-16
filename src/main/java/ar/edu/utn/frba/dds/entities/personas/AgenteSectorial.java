package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.mediciones.Reporte;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportes;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "AGENTE_SECTORIAL")
public class AgenteSectorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Transient
    private AreaSectorial area;

    @Transient
    private ContactoMail contactoMail;

    @Transient
    private String telefono;

    @Transient
    private List<Reporte> reportes;

    public AgenteSectorial(AreaSectorial areaSectorial) {
        this.reportes = new ArrayList<>();
        this.area = areaSectorial;
    }

    public AgenteSectorial(AreaSectorial areaSectorial, ContactoMail contactoMail, String telefono, List<Reporte> reportes) {
        this.area = areaSectorial;
        this.contactoMail = contactoMail;
        this.telefono = telefono;
        this.reportes = reportes;
    }


    public AgenteSectorial() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
            List<Medible> tramos = organizacion.getMiembros().stream()
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

    public void hacerReporte(NotificadorReportes notificador, Integer anio, Integer mes) throws MedicionSinFactorEmisionException {
        notificador.setAgente(this)
                .setInformacionReporte(this.crearReporte(anio, mes))
                .notificarReporte();
    }

    private boolean perteneceAPeriodo(Medicion medicion, Integer anio, Integer mes) {
        return medicion.perteneceAPeriodo(anio, mes);
    }

    private boolean perteneceAPeriodo(Trayecto trayecto, Integer anio, Integer mes) {
        return trayecto.perteneceAPeriodo(anio, mes);
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Reporte> getReportes() {
        return reportes;
    }

    public ContactoMail getContactoMail() {
        return contactoMail;
    }

    public void setContactoMail(ContactoMail contactoMail) {
        this.contactoMail = contactoMail;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setArea(AreaSectorial area) {this.area = area;}

    public void setReportes(List<Reporte> reportes) {this.reportes = reportes;}

    @Override
    public String toString() {
        return '\n' + "Mail : " + this.contactoMail.getDireccionEMail() + '\n' + "Telefono : " + this.telefono + '\n';
    }
}
