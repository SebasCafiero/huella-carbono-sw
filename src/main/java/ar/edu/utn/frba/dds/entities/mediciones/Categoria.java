package ar.edu.utn.frba.dds.entities.mediciones;

import javax.persistence.*;
import java.util.Objects;

//@Entity
//@Table(name = "CATEGORIA")
@Embeddable
public class Categoria {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;

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
    public String toString() {
        return actividad + " - " + tipoConsumo;
    }

//    public Integer getId() {return id;}

//    public void setId(Integer id) {this.id = id;}

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(getActividad(), categoria.getActividad()) && Objects.equals(getTipoConsumo(), categoria.getTipoConsumo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActividad(), getTipoConsumo());
    }
}
