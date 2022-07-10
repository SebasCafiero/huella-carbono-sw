package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;

import java.util.List;

public class Tramo implements Medible {
    private MedioDeTransporte medioDeTransporte;
    private Coordenada coordenadaInicial;
    private Coordenada coordenadaFinal;
//    private List<Trayecto> trayectos;
    private Trayecto trayecto;

    public Tramo(MedioDeTransporte medioDeTransporte, Coordenada coordInicial, Coordenada coordFinal){
        this.medioDeTransporte = medioDeTransporte;
        this.coordenadaInicial = coordInicial;
        this.coordenadaFinal = coordFinal;
    }

    public MedioDeTransporte getMedioDeTransporte(){
        return medioDeTransporte;
    }

    public Coordenada getCoordenadaInicial() {
        return coordenadaInicial;
    }

    public Coordenada getCoordenadaFinal() {
        return coordenadaFinal;
    }

    public Float calcularDistancia(){
        return medioDeTransporte.calcularDistancia(this);
    }

    @Override
    public String getUnidad() {
        return "km";
    }

    @Override
    public Float getValor() {
        return this.calcularDistancia();
    }

    @Override
    public String getCategoria() {
        return "Traslado de Miembros - " + medioDeTransporte.getCategoria();
    }
}
