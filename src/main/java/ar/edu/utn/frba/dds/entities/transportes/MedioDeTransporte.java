package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.AdaptadorServicioDDSTPA;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.CalculadoraDistancias;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ServicioSimulado;

import javax.persistence.*;

@Entity
@Table(name = "MEDIO_DE_TRANSPORTE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioDeTransporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medio_id")
    private Integer id;

    @Transient
    protected CalculadoraDistancias servicioDistancias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float calcularDistancia(Tramo tramo) {
        return this.servicioDistancias.calcularDistancia(tramo.getUbicacionInicial(), tramo.getUbicacionFinal());
    }

    public void setServicioDistancias(CalculadoraDistancias servicioDistancias) {
        this.servicioDistancias = servicioDistancias;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

    public abstract String getClasificacion(); //todo para web

    public abstract String getCategoria();
}
