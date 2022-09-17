package ar.edu.utn.frba.dds.entities.mediciones;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "actividad")
    private String actividad;

    @Column(name = "tipo_consumo")
    private String tipoConsumo;

    public Categoria(String actividad, String tipoConsumo) {
        this.actividad = actividad;
        this.tipoConsumo = tipoConsumo;
    }

    public Categoria() {
    }

    @Override
    public String toString() { return actividad + " - " + tipoConsumo;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getActividad() {return actividad;}

    public void setActividad(String actividad) {this.actividad = actividad;}

    public String getTipoConsumo() {return tipoConsumo;}
}
