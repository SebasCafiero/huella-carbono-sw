package ar.edu.utn.frba.dds.entities.mediciones;

import ar.edu.utn.frba.dds.mihuella.fachada.Medible;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "MEDICION")
public class Medicion implements Medible {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @ManyToOne(fetch = FetchType.EAGER, optional = true)
//    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    @Embedded
    private Categoria categoria;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "valor")
    private Float valor;
    
    @Embedded
    @AttributeOverride(name = "periodicidad",column = @Column(name = "periodicidad"))
    private Periodo periodo;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "batch_id", referencedColumnName = "id")
//    private BatchMedicion batchMedicion;

    public Medicion(Categoria categoria, String unidad, Float valor) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodo = new Periodo(LocalDate.now().getYear(), LocalDate.now().getMonthValue()); //todo para que no sea null
    }

    public Medicion() {} //TODO

    public Medicion(Categoria categoria, String unidad, float valor, Periodo periodo) {
        this.categoria = categoria;
        this.unidad = unidad;
        this.valor = valor;
        this.periodo = periodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Categoria getMiCategoria() {
        return this.categoria;
    }

    @Override
    public String getCategoria() {
        return categoria.toString();
    }

    public void setCategoria (Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return  '\n' + "Medicion { " + "valor = " + valor.toString() + ", unidad = " + unidad + " }";
    }

    public boolean perteneceAPeriodo(Integer anio, Integer mes) {
        return periodo.incluye(anio, mes);
    }
}
