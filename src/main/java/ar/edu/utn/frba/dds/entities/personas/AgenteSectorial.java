package ar.edu.utn.frba.dds.entities.personas;

import ar.edu.utn.frba.dds.entities.lugares.AreaSectorial;
import ar.edu.utn.frba.dds.entities.medibles.ReporteAgente;

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

    @OneToOne(mappedBy = "agente", cascade = CascadeType.ALL)
    private AreaSectorial area;

    @OneToOne(mappedBy = "agente", cascade = CascadeType.ALL)
    private ContactoMail mail;

    @OneToOne(mappedBy = "agente", cascade = CascadeType.ALL)
    private ContactoTelefono telefono;

    @Transient
    private List<ReporteAgente> reportes;

    public AgenteSectorial() {
        this.reportes = new ArrayList<>();
    }

    public AgenteSectorial(AreaSectorial areaSectorial) {
        this.reportes = new ArrayList<>();
        this.area = areaSectorial;
        this.area.setAgente(this);
    }

    public AgenteSectorial(AreaSectorial areaSectorial, ContactoMail contactoMail, ContactoTelefono telefono) {
        this.area = areaSectorial;
        this.area.setAgente(this);
        this.mail = contactoMail;
        contactoMail.setAgente(this);
        this.telefono = telefono;
        telefono.setAgente(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContactoMail getMail() {
        return mail;
    }

    public void setMail(ContactoMail mail) {
        this.mail = mail;
    }

    public ContactoTelefono getTelefono() {
        return telefono;
    }

    public void setTelefono(ContactoTelefono telefono) {
        this.telefono = telefono;
    }

    public List<ReporteAgente> getReportes() {
        return reportes;
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
