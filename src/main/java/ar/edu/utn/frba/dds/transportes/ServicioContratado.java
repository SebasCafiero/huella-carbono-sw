package ar.edu.utn.frba.dds.transportes;

public class ServicioContratado extends MedioDeTransporte {
    private TipoServicio tipo;

    public ServicioContratado(TipoServicio tipoServicio){
        tipo = tipoServicio;
    }

    @Override
    public boolean matchAtributo1(String atributo) {
        return tipo.getNombre().equals(atributo);
    }

    @Override
    public boolean matchAtributo2(String atributo) {
        return true;
    }

    @Override
    public String toString() {
        return "contratado";
    }
}


/* Ejemplos de uso:
 * taxi = new ServicioContratado(new TipoServicio("taxi"));
 * remis = new ServicioContratado(new TipoServicio("remis"));
 * uber = new ServicioContratado(new TipoServicio("uber"));
 * didi = new ServicioContratado(new TipoServicio("didi"));
 * cabify = new ServicioContratado(new TipoServicio("cabify"));
 * */
