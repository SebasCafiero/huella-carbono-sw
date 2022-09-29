package ar.edu.utn.frba.dds.entities.transportes;

import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.AdaptadorServicioDDSTPA;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.CalculadoraDistancias;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ServicioSimulado;

public abstract class MedioDeTransporte {
    private Integer id;

    protected CalculadoraDistancias servicioDistancias;
//        CalculadoraDistancias servicioContratado = new AdaptadorServicioDDSTPA();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float calcularDistancia(Tramo tramo) {
//        throw new RuntimeException("Dead.");
        return this.servicioDistancias.calcularDistancia(tramo.getUbicacionInicial(), tramo.getUbicacionFinal());
    }

    public void setServicioDistancias(CalculadoraDistancias servicioDistancias) {
        this.servicioDistancias = servicioDistancias;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

    public abstract String getCategoria();
}
