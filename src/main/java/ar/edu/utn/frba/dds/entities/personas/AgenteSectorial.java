package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.mediciones.Medicion;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;
import ar.edu.utn.frba.dds.mihuella.MedicionSinFactorEmisionException;
import ar.edu.utn.frba.dds.mihuella.fachada.FachadaOrganizacion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.servicios.reportes.NotificadorReportes;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;

import javax.persistence.*;
import java.time.LocalDate;
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
    private List<ReporteAgente> reportes;

    public AgenteSectorial() {
    }

    public AgenteSectorial(AreaSectorial areaSectorial) {
        this.reportes = new ArrayList<>();
        this.area = areaSectorial;
    }

    public AgenteSectorial(AreaSectorial areaSectorial, ContactoMail contactoMail, String telefono) {
        this.area = areaSectorial;
        this.contactoMail = contactoMail;
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<ReporteAgente> getReportes() {
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

    public void setReportes(List<ReporteAgente> reportes) {this.reportes = reportes;}

    @Override
    public String toString() {
        return '\n' + "Mail : " + this.contactoMail.getDireccionEMail() + '\n' + "Telefono : " + this.telefono + '\n';
    }
}
