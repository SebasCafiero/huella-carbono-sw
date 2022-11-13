package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.CalculadoraDistanciasFactory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "VEHICULO_PARTICULAR")
@PrimaryKeyJoinColumn(name = "particular_id")
public class VehiculoParticular extends MedioDeTransporte {
    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipo;
    @Enumerated(EnumType.STRING)
    private TipoCombustible combustible;

    public VehiculoParticular() {
        servicioDistancias = CalculadoraDistanciasFactory.get();
    }

    public VehiculoParticular(TipoVehiculo tipo, TipoCombustible combustible){
        this.tipo = tipo;
        this.combustible = combustible;
        servicioDistancias = CalculadoraDistanciasFactory.get();
    }

    @Override
    public String toString() {
        return "particular " + this.tipo.toString() + " " + this.combustible.toString();
    }

    @Override
    public String getClasificacion() {
        return tipo.toString() + " - " + combustible.toString();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        VehiculoParticular other = (VehiculoParticular) obj;
        if (!Objects.equals(tipo, other.tipo)) return false;
        return Objects.equals(combustible, other.combustible);
    }

    @Override
    public String getCategoria() {
        return "Particular - " + combustible.toString();
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public TipoCombustible getCombustible() {
        return combustible;
    }

    public void setCombustible(TipoCombustible combustible) {
        this.combustible = combustible;
    }
}
