package ar.edu.utn.frba.dds.lugares;

import java.util.HashSet;
import java.util.Set;

public class UbicacionGeografica {
    private AreaSectorial sector;
    private Coordenada coordenada;
    private Direccion direccion;

    public UbicacionGeografica(String lugar, Coordenada coordenada) {
        this.sector = new AreaSectorial(lugar);
        this.coordenada = coordenada;
        this.direccion = new Direccion(coordenada);
    }

    public UbicacionGeografica(String lugar, Float latitud, Float longitud){
        this.sector = new AreaSectorial(lugar);
        this.coordenada = new Coordenada(latitud,longitud);
        this.direccion = new Direccion(this.coordenada);
    }

    public AreaSectorial getSector() {
        return sector;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

}
