package ar.edu.utn.frba.dds.trayectos;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;

import java.util.List;

public class Tramo implements Medible {
    private MedioDeTransporte medioDeTransporte;
    private UbicacionGeografica ubicacionInicial;
    private UbicacionGeografica ubicacionFinal;
//    private List<Trayecto> trayectos;
    private Trayecto trayecto;

    public Tramo(MedioDeTransporte medioDeTransporte, String lugarInicial, Coordenada coordInicial, String lugarFinal, Coordenada coordFinal){
        this.medioDeTransporte = medioDeTransporte;
        this.ubicacionInicial = new UbicacionGeografica(lugarInicial, coordInicial);
        this.ubicacionFinal = new UbicacionGeografica(lugarFinal, coordFinal);
    }

    public Tramo(MedioDeTransporte medioDeTransporte, Coordenada coordInicial, Coordenada coordFinal){ //TODO
        this.medioDeTransporte = medioDeTransporte;
        this.ubicacionInicial = new UbicacionGeografica("BsAs", coordInicial);
        this.ubicacionFinal = new UbicacionGeografica("BsAs", coordFinal);
    }

    public Tramo(MedioDeTransporte medioDeTransporte, UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal){
        this.medioDeTransporte = medioDeTransporte;
        this.ubicacionInicial = ubicacionInicial;
        this.ubicacionFinal = ubicacionFinal;
    }

    public MedioDeTransporte getMedioDeTransporte(){
        return medioDeTransporte;
    }

    public Coordenada getCoordenadaInicial() {
        return ubicacionInicial.getCoordenada();
    }

    public Coordenada getCoordenadaFinal() {
        return ubicacionFinal.getCoordenada();
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
