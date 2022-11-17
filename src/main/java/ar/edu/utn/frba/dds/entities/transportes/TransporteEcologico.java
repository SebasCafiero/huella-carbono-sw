package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.servicios.clients.calculadoraDistancias.CalculadoraDistanciasFactory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TRANSPORTE_ECOLOGICO")
@PrimaryKeyJoinColumn(name = "ecologico_id")
public class TransporteEcologico extends MedioDeTransporte {
    @Enumerated(EnumType.STRING)
    private TipoTransporteEcologico tipo;

    public TransporteEcologico() {
        servicioDistancias = CalculadoraDistanciasFactory.get();
    }

    public TransporteEcologico(TipoTransporteEcologico tipo){
        this.tipo = tipo;
        servicioDistancias = CalculadoraDistanciasFactory.get();
    }

    @Override
    public String toString() {
        return "ecologico";
    }

    @Override
    public String getClasificacion() {
        return tipo.toString();
    }

    @Override
    public String getCategoria() {
        return "Ecologico";
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
        TransporteEcologico other = (TransporteEcologico) obj;
        return Objects.equals(tipo, other.tipo);
    }

    public TipoTransporteEcologico getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransporteEcologico tipo) {
        this.tipo = tipo;
    }
}
