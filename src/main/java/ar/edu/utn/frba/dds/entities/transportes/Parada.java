package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;

import javax.persistence.*;

@Entity
@Table(name = "PARADA")
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parada_id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "ubicacion_id")
    private UbicacionGeografica ubicacion;

    @Column(name = "distancia_anterior")
    private Float distanciaAnterior;

    @Column(name = "distancia_proxima")
    private Float distanciaProxima;
//La distanciaProxima de una parada deberia ser igual a la distanciaAnterior de la parada siguiente (VALIDAR)

    public Parada() {
    }

    public Parada(Direccion direccion, Coordenada coordenada, Float distanciaAnterior, Float distanciaProxima) {
        this.ubicacion = new UbicacionGeografica(direccion, coordenada);
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Parada(UbicacionGeografica ubicacion, Float distanciaAnterior, Float distanciaProxima) {
        this.ubicacion = ubicacion;
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Parada(Coordenada coordenada, Float distanciaAnterior, Float distanciaProxima) {
        this.ubicacion = new UbicacionGeografica(coordenada);
        this.distanciaAnterior = distanciaAnterior;
        this.distanciaProxima = distanciaProxima;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UbicacionGeografica getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionGeografica ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDistanciaAnterior(Float distanciaAnterior) {
        this.distanciaAnterior = distanciaAnterior;
    }

    public void setDistanciaProxima(Float distanciaProxima) {
        this.distanciaProxima = distanciaProxima;
    }

    public Coordenada getCoordenada() {
        return ubicacion.getCoordenada();
    }

    public Float getDistanciaAnterior() {
        return distanciaAnterior;
    }

    public Float getDistanciaProxima() {
        return distanciaProxima;
    }


}
