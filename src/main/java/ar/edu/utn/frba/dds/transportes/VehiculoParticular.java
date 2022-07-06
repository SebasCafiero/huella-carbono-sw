package ar.edu.utn.frba.dds.transportes;

import java.util.Objects;

public class VehiculoParticular extends MedioDeTransporte {
    private final TipoVehiculo tipo;
    private final TipoCombustible combustible;

    public VehiculoParticular(String [] argumentos) {
        this.tipo = TipoVehiculo.valueOf(argumentos[0]);
        this.combustible = TipoCombustible.valueOf(argumentos[1]);
    }

    public VehiculoParticular(TipoVehiculo tipo, TipoCombustible combustible){
        this.tipo = tipo;
        this.combustible = combustible;
    }

//    @Override
//    public boolean matchAtributo1(String atributo) {
//        try {
//            TipoVehiculo tipo = TipoVehiculo.valueOf(atributo);
//        } catch (IllegalArgumentException ex) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean matchAtributo2(String atributo) {
//        try {
//            TipoCombustible tipo = TipoCombustible.valueOf(atributo);
//        } catch (IllegalArgumentException ex) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "particular " + this.tipo.toString() + " " + this.combustible.toString();
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
}
