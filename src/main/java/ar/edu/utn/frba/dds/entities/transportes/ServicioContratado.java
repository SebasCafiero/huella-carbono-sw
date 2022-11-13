package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.CalculadoraDistanciasFactory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SERVICIO_CONTRATADO")
@PrimaryKeyJoinColumn(name = "contratado_id")
public class ServicioContratado extends MedioDeTransporte {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_servicio_id")
    private TipoServicio tipo;

    public ServicioContratado() {
        servicioDistancias = CalculadoraDistanciasFactory.get();
    }

    public ServicioContratado(TipoServicio tipoServicio) {
        tipo = tipoServicio;
        servicioDistancias = CalculadoraDistanciasFactory.get();
    }

    @Override
    public String toString() {
        return "contratado - "  + tipo.getNombre();
    }

    @Override
    public String getClasificacion() {
        return tipo.toString();
    }

    @Override
    public String getCategoria() {
        return "Contratado";
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoServicio tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicioContratado)) return false;
        ServicioContratado that = (ServicioContratado) o;
        return Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo);
    }
}


/* Ejemplos de uso:
 * taxi = new ServicioContratado(new TipoServicio("taxi"));
 * remis = new ServicioContratado(new TipoServicio("remis"));
 * uber = new ServicioContratado(new TipoServicio("uber"));
 * didi = new ServicioContratado(new TipoServicio("didi"));
 * cabify = new ServicioContratado(new TipoServicio("cabify"));
 * */