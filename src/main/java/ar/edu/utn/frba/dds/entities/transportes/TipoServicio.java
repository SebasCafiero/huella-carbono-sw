package ar.edu.utn.frba.dds.entities.transportes;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TIPO_SERVICIO")
public class TipoServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    public TipoServicio() {
    }

    public TipoServicio(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoServicio)) return false;
        TipoServicio that = (TipoServicio) o;
        return Objects.equals(getNombre(), that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

/* Ejemplos de uso:
* taxi = new TipoServicio("taxi");
* remis = new TipoServicio("remis");
* uber = new TipoServicio("uber");
* didi = new TipoServicio("didi");
* cabify = new TipoServicio("cabify");
* */
