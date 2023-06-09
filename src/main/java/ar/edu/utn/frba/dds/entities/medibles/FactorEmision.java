package ar.edu.utn.frba.dds.entities.medibles;

import javax.persistence.*;

@Entity
@Table(name = "FACTOR_DE_EMISION")
public class FactorEmision {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "factor_id")
    private Integer id;

    @Embedded
    private Categoria categoria;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "valor")
    private Float valor;

    public FactorEmision() {
    }

    public FactorEmision(Categoria categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return this.categoria.toString();
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {this.unidad = unidad;}

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "FactorEmision{" +
                "id=" + id +
                ", categoria=" + categoria +
                ", unidad='" + unidad + '\'' +
                ", valor=" + valor +
                '}';
    }
}
