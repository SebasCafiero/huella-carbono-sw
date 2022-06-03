package ar.edu.utn.frba.dds.transportes;

public class VehiculoParticular extends MedioDeTransporteConServicioExterno{
    private TipoVehiculo tipo;
    private TipoCombustible combustible;

    public VehiculoParticular(TipoVehiculo tipo, TipoCombustible combustible){
        this.tipo = tipo;
        this.combustible = combustible;
    }
}
