package ar.edu.utn.frba.dds.entities.mediciones;

import ar.edu.utn.frba.dds.entities.lugares.Organizacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "BATCH_MEDICIONES")
public class BatchMedicion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_id")
    private List<Medicion> mediciones;

    @Column
    private LocalDate fecha;

    @Column
    private Integer cantidadMediciones;

    @Transient
    private Organizacion organizacion;

    public BatchMedicion() {
    }

    public BatchMedicion(List<Medicion> mediciones, LocalDate fecha) {
        this.mediciones = mediciones;
        this.cantidadMediciones = mediciones.size();
        this.fecha = fecha;
    }

//    public BatchMedicion(List<Medicion> mediciones) {
//        this.mediciones = mediciones;
//        this.cantidadMediciones = mediciones.size();
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = LocalDate.parse(fecha);
    }

    public Integer getCantidadMediciones() {
        return cantidadMediciones;
    }

    public void setCantidadMediciones(Integer cantidadMediciones) {
        this.cantidadMediciones = cantidadMediciones;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    //    @Override
//    public String toString() {
//        return "El batch de mediciones de id " + getId().toString() + " fue cargado en la fecha " + getFecha().toString() + " y tiene " + getCantidadMediciones().toString() + " mediciones." + '\n';
//    }

    @Override
    public String toString() {
        return "\nBatch id: " + id.toString() +
                "\nFecha: " + fecha.toString() +
                "\nMediciones: " + mediciones.toString();
    }
}

