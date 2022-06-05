package ar.edu.utn.frba.dds.transportes;

public class ServicioContratado extends MedioDeTransporteConServicioExterno{
    private TipoServicio tipo;

    public ServicioContratado(TipoServicio tipoServicio){
        tipo = tipoServicio;
    }

}


/* Ejemplos de uso:
 * taxi = new ServicioContratado(new TipoServicio("taxi"));
 * remis = new ServicioContratado(new TipoServicio("remis"));
 * uber = new ServicioContratado(new TipoServicio("uber"));
 * didi = new ServicioContratado(new TipoServicio("didi"));
 * cabify = new ServicioContratado(new TipoServicio("cabify"));
 * */
