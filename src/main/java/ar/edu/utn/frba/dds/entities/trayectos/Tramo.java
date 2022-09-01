package ar.edu.utn.frba.dds.entities.trayectos;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;


public class Tramo implements Medible {
    private MedioDeTransporte medioDeTransporte;
    private UbicacionGeografica ubicacionInicial;
    private UbicacionGeografica ubicacionFinal;
//    private List<Trayecto> trayectos;
    private Trayecto trayecto;
    private Float valor;

    public Tramo(MedioDeTransporte medioDeTransporte, Direccion direccionInicial, Coordenada coordInicial, Direccion direccionFinal, Coordenada coordFinal){
        this.medioDeTransporte = medioDeTransporte;
        this.ubicacionInicial = new UbicacionGeografica(direccionInicial, coordInicial);
        this.ubicacionFinal = new UbicacionGeografica(direccionFinal, coordFinal);
    }

    public Tramo(MedioDeTransporte medioDeTransporte, Coordenada coordInicial, Coordenada coordFinal){
        this.medioDeTransporte = medioDeTransporte;
        this.ubicacionInicial = new UbicacionGeografica(coordInicial);
        this.ubicacionFinal = new UbicacionGeografica(coordFinal);
    }

    public Tramo(MedioDeTransporte medioDeTransporte, UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal){
        this.medioDeTransporte = medioDeTransporte;
        this.ubicacionInicial = ubicacionInicial;
        this.ubicacionFinal = ubicacionFinal;
    }

    public MedioDeTransporte getMedioDeTransporte(){
        return medioDeTransporte;
    }

    public UbicacionGeografica getUbicacionInicial() {
        return ubicacionInicial;
    }

    public UbicacionGeografica getUbicacionFinal() {
        return ubicacionFinal;
    }

    private Float calcularDistancia(){
        return medioDeTransporte.calcularDistancia(this);
    }

    @Override
    public String getUnidad() {
        return "km";
    }

    @Override
    public Float getValor() {
        //return this.calcularDistancia();
        if(this.valor == null) this.valor = this.calcularDistancia();
        return this.valor;
    }

    @Override
    public String getCategoria() {
        return "Traslado de Miembros - " + medioDeTransporte.getCategoria();
    }

    @Override
    public String toString() {
        return "Tramo{" +
                "medioDeTransporte=" + medioDeTransporte +
                ", ubicacionInicial=" + ubicacionInicial +
                ", ubicacionFinal=" + ubicacionFinal +
                ", trayecto=" + trayecto +
                ", valor=" + valor +
                '}';
    }
}