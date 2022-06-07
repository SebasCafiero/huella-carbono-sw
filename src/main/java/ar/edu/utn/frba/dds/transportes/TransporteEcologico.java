package ar.edu.utn.frba.dds.transportes;

public class TransporteEcologico extends MedioDeTransporte {
    private TipoTransporteEcologico tipo;

    public TransporteEcologico(TipoTransporteEcologico tipo){
        this.tipo = tipo;
    }

    @Override
    public boolean matchAtributo1(String atributo) {
        return true;
    }

    @Override
    public boolean matchAtributo2(String atributo) {
        return true;
    }

    @Override
    public String toString() {
        return "ecologico";
    }
}
