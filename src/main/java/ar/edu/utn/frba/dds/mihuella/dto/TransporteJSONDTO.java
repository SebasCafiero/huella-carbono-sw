package ar.edu.utn.frba.dds.mihuella.dto;

import java.util.List;

public class TransporteJSONDTO {
    public String transporte; //todos
    public String tipo; //todos
    public String subtipo; //todos

    public String combustible; //particular
    public String linea; //publico
    public List<ParadaDTO> paradas; //publico

    public class ParadaDTO {
        public Float latitud;
        public Float longitud;
        public Float distanciaAnterior;
        public Float distanciaProxima;
    }
}
