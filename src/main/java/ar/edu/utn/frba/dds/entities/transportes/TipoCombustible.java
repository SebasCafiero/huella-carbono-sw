package ar.edu.utn.frba.dds.entities.transportes;

import java.util.Arrays;

public enum TipoCombustible {
    GNC,
    GASOIL,
    NAFTA,
    ELECTRICO;

    public static Boolean hasValue(String tipo) {
        return Arrays.stream(values()).anyMatch(unTipo -> unTipo.name().equals(tipo));
    }
}
