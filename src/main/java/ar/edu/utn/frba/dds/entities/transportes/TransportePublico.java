package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.entities.exceptions.TransportePublicoSinParadaException;
import ar.edu.utn.frba.dds.entities.lugares.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Direccion;
import ar.edu.utn.frba.dds.entities.medibles.Tramo;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TRANSPORTE_PUBLICO")
@PrimaryKeyJoinColumn(name = "publico_id")
public class TransportePublico extends MedioDeTransporte {
    @Enumerated(EnumType.STRING)
    private TipoTransportePublico tipo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderColumn(name = "posicion")
    @JoinColumn(name = "medio_id")
    private List<Parada> paradas;

    @Column(name = "linea")
    private String linea;

    public TransportePublico() {
        this.paradas = new LinkedList<>();
    }

    public TransportePublico(TipoTransportePublico tipo, String linea) {
        this.tipo = tipo;
        this.linea = linea;
        this.paradas = new LinkedList<>();
    }

    public void agregarParada(Parada parada){
        this.paradas.add(parada);
    }

    public void agregarParadas(Parada... paradas) {
        Collections.addAll(this.paradas, paradas);
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    @Override
    public Float calcularDistancia(Tramo tramo) {
        Parada paradaInicial = buscarParada(tramo.getUbicacionInicial().getCoordenada());
        Parada paradaFinal = buscarParada(tramo.getUbicacionFinal().getCoordenada());
//        if (paradaInicial == null || paradaFinal == null) {
//            throw new TransportePublicoSinParadaException();
//        }
        if (paradaInicial.getCoordenada().getLatitud().equals(0F) || paradaFinal.getCoordenada().getLatitud().equals(0F)) {
            throw new TransportePublicoSinParadaException();
        }

        int nroParadaInicial = paradas.indexOf(paradaInicial);
        int nroParadaFinal = paradas.indexOf(paradaFinal);

        if(nroParadaInicial < nroParadaFinal) {
            return (float) paradas.subList(nroParadaInicial, nroParadaFinal).stream()
                    .mapToDouble(Parada::getDistanciaProxima).reduce(0, Double::sum);
        } else {
            return (float) paradas.subList(nroParadaFinal + 1, nroParadaInicial + 1).stream()
                    .mapToDouble(Parada::getDistanciaAnterior).reduce(0, Double::sum);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof TransportePublico)) return false;
        TransportePublico that = (TransportePublico) o;
        return getTipo() == that.getTipo() && Objects.equals(getLinea(), that.getLinea());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTipo(), getLinea());
    }

    public Parada buscarParada(Coordenada coordenada) {
        Parada paradaRandom = new Parada(new Direccion(), new Coordenada(0F, 0F), 0F, 0F);
        return this.paradas.stream()
                .filter(parada -> parada.getCoordenada().esIgualAOtraCoordenada(coordenada))
                .findFirst()
                .orElse(paradaRandom);
        // TODO lo puse para pruebas web, si afecta a otra cosa sacarlo -> Esto está feo, lo dejo cambiando algo mio para que no rompa
    }

    @Override
    public String toString() {
        return "publico " + tipo.toString() + " " + linea;
    }

    @Override
    public String getClasificacion() {
        return tipo.toString() + " - " + linea;
    }

    @Override
    public String getCategoria() {
        return "Publico - " + tipo.toString();
    }

    public TipoTransportePublico getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransportePublico tipo) {
        this.tipo = tipo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public void setParadas(LinkedList<Parada> paradas) {
        this.paradas = paradas;
    }
}
