package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.lugares.geografia.AreaSectorial;
import ar.edu.utn.frba.dds.entities.mediciones.ReporteAgente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AGENTE_SECTORIAL")
public class AgenteSectorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agente_id")
    private Integer id;

    @Transient
    private AreaSectorial area;

    @Transient
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "mail_id", referencedColumnName = "contacto_id")
    private ContactoMail contactoMail;

    @Transient
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "telefono_id", referencedColumnName = "contacto_id")
    private ContactoTelefono telefono;

    @Transient
    private List<ReporteAgente> reportes;

    public AgenteSectorial() {
    }

    public AgenteSectorial(AreaSectorial areaSectorial) {
        this.reportes = new ArrayList<>();
        this.area = areaSectorial;
        this.area.setAgente(this);
    }

    public AgenteSectorial(AreaSectorial areaSectorial, ContactoMail contactoMail, ContactoTelefono telefono) {
        this.area = areaSectorial;
        this.area.setAgente(this);
        this.contactoMail = contactoMail;
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContactoTelefono getTelefono() {
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

    public void setTelefono(ContactoTelefono telefono) {
        this.telefono = telefono;
    }

    public void setArea(AreaSectorial area) {
        this.area = area;
    }

    public void agregarReporte(ReporteAgente reporte) {
        this.reportes.add(reporte);
    }

    public void quitarReporte(ReporteAgente reporte) {
        this.reportes.remove(reporte);
    }

    public void setReportes(List<ReporteAgente> reportes) {
        this.reportes = reportes;
    }

    public AreaSectorial getArea() {
        return area;
    }

    @Override
    public String toString() {
        return '\n' + "ID : " + this.id + '\n';
    }
}
