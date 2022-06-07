package ar.edu.utn.frba.dds.transportes;

public class VehiculoParticular extends MedioDeTransporte {
    private TipoVehiculo tipo;
    private TipoCombustible combustible;

    public VehiculoParticular(String [] argumentos) {
        this.tipo = TipoVehiculo.valueOf(argumentos[0]);
        this.combustible = TipoCombustible.valueOf(argumentos[1]);
    }

    public VehiculoParticular(TipoVehiculo tipo, TipoCombustible combustible){
        this.tipo = tipo;
        this.combustible = combustible;
    }

    @Override
    public boolean matchAtributo1(String atributo) {
        try {
            TipoVehiculo tipo = TipoVehiculo.valueOf(atributo);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean matchAtributo2(String atributo) {
        try {
            TipoCombustible tipo = TipoCombustible.valueOf(atributo);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "particular";
    }
}
