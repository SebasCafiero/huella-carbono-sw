package ar.edu.utn.frba.dds.entities.medibles;

import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;

import javax.persistence.*;

@Entity
@Table(name = "TRAMO")
public class Tramo implements Medible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tramo_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER) // No debe haber cascada
    @JoinColumn(name = "medio_id", nullable = false)
    private MedioDeTransporte medioDeTransporte;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ubicacion_inicial", referencedColumnName = "ubicacion_id", nullable = false)
    private UbicacionGeografica ubicacionInicial;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ubicacion_final", referencedColumnName = "ubicacion_id", nullable = false)
    private UbicacionGeografica ubicacionFinal;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto_id", nullable = false)
    private Trayecto trayecto;

    @Column(name = "valor")
    private Float valor;

    @Embedded
    private Categoria categoria;

    public Tramo() {
    }

    public Tramo(MedioDeTransporte medioDeTransporte, Direccion direccionInicial, Coordenada coordInicial, Direccion direccionFinal, Coordenada coordFinal) {
        this.medioDeTransporte = medioDeTransporte;
        this.categoria = new Categoria("Traslado de Miembros", medioDeTransporte.getCategoria());
        this.ubicacionInicial = new UbicacionGeografica(direccionInicial, coordInicial);
        this.ubicacionFinal = new UbicacionGeografica(direccionFinal, coordFinal);
    }

    public Tramo(MedioDeTransporte medioDeTransporte, UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal) {
        this.medioDeTransporte = medioDeTransporte;
        this.categoria = new Categoria("Traslado de Miembros", medioDeTransporte.getCategoria());
        this.ubicacionInicial = ubicacionInicial;
        this.ubicacionFinal = ubicacionFinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUbicacionInicial(UbicacionGeografica ubicacionInicial) {
        this.ubicacionInicial = ubicacionInicial;
    }

    public void setUbicacionFinal(UbicacionGeografica ubicacionFinal) {
        this.ubicacionFinal = ubicacionFinal;
    }

    public Trayecto getTrayecto() {
        return trayecto;
    }

    public void setTrayecto(Trayecto trayecto) {
        this.trayecto = trayecto;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public void setValor() {
        this.valor = this.calcularDistancia();
    }

    public MedioDeTransporte getMedioDeTransporte(){
        return medioDeTransporte;
    }

    public void setMedioDeTransporte(MedioDeTransporte nuevoMedioDeTransporte) {
        this.medioDeTransporte = nuevoMedioDeTransporte;
    }

    public UbicacionGeografica getUbicacionInicial() {
        return ubicacionInicial;
    }

    public UbicacionGeografica getUbicacionFinal() {
        return ubicacionFinal;
    }

    private Float calcularDistancia() {
        return medioDeTransporte.calcularDistancia(this);
    }

    @Override
    public String getUnidad() {
        return "km";
    }

    @Override
    public Float getValor() {
        if(this.valor == null) {
            this.setValor();
        }
        return this.valor;
    }

    @Override
    public String getCategoria() {
        return categoria.toString();
    }

    public Categoria getMiCategoria() {
        return this.categoria;
    }

    @Override
    public String toString() {
        return "Tramo{" +
                "medioDeTransporte=" + medioDeTransporte +
                ", ubicacionInicial=" + ubicacionInicial +
                ", ubicacionFinal=" + ubicacionFinal +
                ", trayectoId=" + trayecto.getId() +
                ", valor=" + valor +
                '}';
    }
}
