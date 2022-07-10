package ar.edu.utn.frba.dds.transportes;

public class ServicioContratado extends MedioDeTransporte {
    private TipoServicio tipo;

    public ServicioContratado(TipoServicio tipoServicio){
        tipo = tipoServicio;
    }

    @Override
    public String toString() {
        return "contratado"  + tipo.getNombre();
    }

    @Override
    public String getCategoria() {
        return "Contratado";
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
        ServicioContratado other = (ServicioContratado) obj;
        return this.tipo.equals(other.tipo);
    }
}


/* Ejemplos de uso:
 * taxi = new ServicioContratado(new TipoServicio("taxi"));
 * remis = new ServicioContratado(new TipoServicio("remis"));
 * uber = new ServicioContratado(new TipoServicio("uber"));
 * didi = new ServicioContratado(new TipoServicio("didi"));
 * cabify = new ServicioContratado(new TipoServicio("cabify"));
 * */