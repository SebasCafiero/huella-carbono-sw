package ar.edu.utn.frba.dds.entities.transportes;

import java.util.Objects;

public class TransporteEcologico extends MedioDeTransporte {
    private TipoTransporteEcologico tipo;

    public TransporteEcologico(TipoTransporteEcologico tipo){
        this.tipo = tipo;
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
