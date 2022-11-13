package ar.edu.utn.frba.dds.cache;

import javax.persistence.Cacheable;

@Cacheable
public class CacheLocalidad {
    private Integer idProvincia;
    private Integer idMunicipio;
    private Integer idLocalidad;

    public CacheLocalidad() {
    }

    public CacheLocalidad(Integer idProvincia, Integer idMunicipio, Integer idLocalidad) {
        this.idProvincia = idProvincia;
        this.idMunicipio = idMunicipio;
        this.idLocalidad = idLocalidad;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }
}
