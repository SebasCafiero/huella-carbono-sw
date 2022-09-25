package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;

public class NoExisteMedioException extends RuntimeException {

    private final MedioDeTransporte medioDeTransporte;

    public NoExisteMedioException(MedioDeTransporte medioDeTransporte) {
        super();
        this.medioDeTransporte = medioDeTransporte;
    }

    public MedioDeTransporte getMedioDeTransporte() {
        return medioDeTransporte;
    }

    @Override
    public String getMessage() {
        return "El medio de transporte " + medioDeTransporte.toString() + " no existe";
    }

}
