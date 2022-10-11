package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ServicioSimulado;

import javax.persistence.*;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "ecologico_id")
@Table(name = "TRANSPORTE_ECOLOGICO")
public class TransporteEcologico extends MedioDeTransporte {
    @Enumerated(EnumType.STRING)
    private TipoTransporteEcologico tipo;

    public TransporteEcologico(TipoTransporteEcologico tipo){
        this.tipo = tipo;
        servicioDistancias = new ServicioSimulado();
    }

    @Override
    public String toString() {
        return "ecologico";
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
}
