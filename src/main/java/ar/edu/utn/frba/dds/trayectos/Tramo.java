package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;

public class Tramo {
    private MedioDeTransporte medioDeTransporte;
    private Coordenada coordenadaInicial;
    private Coordenada coordenadaFinal;

    public Tramo(MedioDeTransporte medioDeTransporte, Coordenada coordInicial, Coordenada coordFinal) {
        this.medioDeTransporte = medioDeTransporte;
        this.coordenadaInicial = coordInicial;
        this.coordenadaFinal = coordFinal;
    }

    public MedioDeTransporte getMedioDeTransporte() {
        return medioDeTransporte;
    }

    public Coordenada getCoordenadaInicial() {
        return coordenadaInicial;
    }

    public Coordenada getCoordenadaFinal() {
        return coordenadaFinal;
    }

    public Float calcularDistancia() {
        return medioDeTransporte.calcularDistancia(this);
    }

    public Boolean equals(Tramo tramo) {
        return this.medioDeTransporte.equals(tramo.getMedioDeTransporte())
                && this.coordenadaInicial.esIgualAOtraCoordenada(tramo.coordenadaInicial)
                && this.coordenadaFinal.esIgualAOtraCoordenada(tramo.coordenadaFinal);
    }

}
